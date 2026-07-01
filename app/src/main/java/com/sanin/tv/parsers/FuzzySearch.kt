package com.sanin.tv.parsers

object FuzzySearch {

    fun ratio(s1: String, s2: String): Int {
        if (s1 == s2) return 100
        if (s1.isEmpty() || s2.isEmpty()) return 0

        val longer = if (s1.length >= s2.length) s1 else s2
        val shorter = if (s1.length < s2.length) s1 else s2

        val distance = levenshtein(longer, shorter)
        val maxLen = longer.length.toDouble()
        return ((1.0 - distance / maxLen) * 100).toInt()
    }

    private fun levenshtein(s1: String, s2: String): Double {
        val m = s1.length
        val n = s2.length
        val dp = Array(m + 1) { 
        I
        for (i in 0..m) dp[i][0] = i
        for (j in 0..n) dp[0][j] = j
        for (i in 1..m) {
            for (j in 1..n) {
                dp[i][j] = if (s1[i - 1] == s2[j - 1]) {
                    dp[i - 1][j - 1]
                } else {
                    1 + minOf(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1])
                }
            }
        }
        return dp[m][n].toDouble()
    }
}
