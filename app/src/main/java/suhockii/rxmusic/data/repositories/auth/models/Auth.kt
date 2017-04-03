package suhockii.rxmusic.data.repositories.auth.models

/** Created by Maksim Sukhotski on 3/27/2017.*/
class Auth(val access_token: String,
           val secret: String,
           val expires_in: String,
           val user_id: String)