package lib.mobile.droid.networkmodule

interface IConnectionStateChecker {
    /**
     * Метод для проверки доступности интернет соединения
     */
    fun checkForConnectionState()
}