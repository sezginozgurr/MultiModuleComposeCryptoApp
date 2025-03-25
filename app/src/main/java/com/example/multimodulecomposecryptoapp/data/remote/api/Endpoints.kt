package com.example.multimodulecomposecryptoapp.data.remote.api

object Endpoints {

    const val COINS = "v2/coins"
    const val COIN_DETAILS = "v2/coin"
    const val COINID = "coinId"

    object Params {
        const val LIMIT = "limit"
        const val OFFSET = "offset"
        const val ORDER_BY = "orderBy"
        const val ORDER_DIRECTION = "orderDirection"
        const val UUID = "uuid"

        object DefaultValues {
            const val DEFAULT_LIMIT = 50
            const val DEFAULT_OFFSET = 0
            const val DEFAULT_ORDER_BY = "marketCap"
            const val DEFAULT_ORDER_DIRECTION = "desc"
        }
    }

}