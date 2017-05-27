package rx.music.ui.audio

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import rx.music.net.BaseFields.Companion.PAGINATION_COUNT

/** Created by Maksim Sukhotski on 4/9/2017. */
class PaginationScrollListener(
        val func: (paginationCount: Int) -> Unit = { _ -> run {} },
        val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private var visibleThreshold = 4
    private var paginationCount = PAGINATION_COUNT
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy > 0) {
            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }
            if (!loading
                    && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)
                    || firstVisibleItem > paginationCount) {
                func(paginationCount)
                loading = true
                paginationCount += PAGINATION_COUNT
            }
        }
    }

}