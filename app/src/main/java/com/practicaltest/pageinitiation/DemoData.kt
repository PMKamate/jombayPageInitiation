package com.practicaltest.pageinitiation

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DemoData {
    @SerializedName("char_id")
    @Expose
    var charId: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("birthday")
    @Expose
    var birthday: String? = null

    @SerializedName("occupation")
    @Expose
    var occupation: List<String>? = null

    @SerializedName("img")
    @Expose
    var img: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("nickname")
    @Expose
    var nickname: String? = null

    @SerializedName("appearance")
    @Expose
    var appearance: List<Int>? = null

    @SerializedName("portrayed")
    @Expose
    var portrayed: String? = null

    @SerializedName("category")
    @Expose
    var category: String? = null

    @SerializedName("better_call_saul_appearance")
    @Expose
    var betterCallSaulAppearance: List<Int>? = null
}