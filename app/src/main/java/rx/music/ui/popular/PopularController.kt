package rx.music.ui.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import rx.music.R
import rx.music.ui.base.MoxyController

/** Created by Maksim Sukhotski on 5/1/2017. */
class PopularController : MoxyController(), PopularView {

    @InjectPresenter
    lateinit var popularPresenter: PopularPresenter

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_popular, container, false)
    }

    override fun onViewBound(view: View) {
        with(view) {
            //            val linearLayoutManager = LinearLayoutManager(activity)
//            audioRecyclerView.setHasFixedSize(true)
//            audioRecyclerView.layoutManager = linearLayoutManager
//            audioRecyclerView.adapter = adapter
//            audioRecyclerView.addOnScrollListener(InfiniteScrollListener({
//                offset += 30
//                audioPresenter.getAudio(offset = offset.toString())
//            }, linearLayoutManager))
        }
    }
}