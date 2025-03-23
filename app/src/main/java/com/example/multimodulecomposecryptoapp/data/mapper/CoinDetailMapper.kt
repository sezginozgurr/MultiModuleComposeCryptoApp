package com.example.multimodulecomposecryptoapp.data.mapper

import com.example.multimodulecomposecryptoapp.data.remote.dto.CoinDetailDto
import com.example.multimodulecomposecryptoapp.domain.model.CoinDetail
import java.math.BigDecimal
import java.util.Date

fun CoinDetailDto.toCoinDetail(isFavorite: Boolean = false): CoinDetail {
    return CoinDetail(
        id = uuid,
        symbol = symbol,
        name = name,
        description = description ?: "",
        imageUrl = imageUrl,
        price = try { BigDecimal(price) } catch (e: Exception) { BigDecimal.ZERO },
        marketCap = try { BigDecimal(marketCap) } catch (e: Exception) { BigDecimal.ZERO },
        volume = try { BigDecimal(volume) } catch (e: Exception) { BigDecimal.ZERO },
        rank = rank,
        priceChangePercentage24h = try { priceChangePercentage24h.toDouble() } catch (e: Exception) { 0.0 },
        allTimeHighPrice = try { BigDecimal(allTimeHigh.price) } catch (e: Exception) { BigDecimal.ZERO },
        allTimeHighDate = Date(allTimeHigh.timestamp * 1000),
        totalSupply = if (supply.total.isNotEmpty()) try { BigDecimal(supply.total) } catch (e: Exception) { BigDecimal.ZERO } else BigDecimal.ZERO,
        circulatingSupply = if (supply.circulating.isNotEmpty()) try { BigDecimal(supply.circulating) } catch (e: Exception) { BigDecimal.ZERO } else BigDecimal.ZERO,
        listingDate = Date(listedAt * 1000),
        numberOfMarkets = numberOfMarkets,
        numberOfExchanges = numberOfExchanges,
        websiteUrl = links?.firstOrNull { it.type == "website" }?.url ?: "",
        isFavorite = isFavorite
    )
} 