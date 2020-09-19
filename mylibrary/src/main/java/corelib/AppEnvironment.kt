package corelib

/**
 * 接続先環境
 * Created by Yamamoto Keita on 2018/01/17.
 */
object AppEnvironment {

    val instance: AppEnvironment
        get() {
            return this
        }

    var mtsBaseURL = "https://sbi-toshin-stub.cross-dev.net/kabustub/yamamoto"
}
