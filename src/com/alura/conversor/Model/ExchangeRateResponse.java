package com.alura.conversor.Model;

import com.google.gson.annotations.SerializedName;

public record ExchangeRateResponse(
        @SerializedName("conversion_rate")
        double conversionRate) {
}
