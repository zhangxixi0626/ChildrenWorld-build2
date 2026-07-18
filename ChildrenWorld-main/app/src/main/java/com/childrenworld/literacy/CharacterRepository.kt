package com.childrenworld.literacy

/**
 * Data class representing a Chinese character with pronunciation and meaning
 */
data class ChineseCharacter(
    val character: String,
    val pinyin: String,
    val meaning: String
)

/**
 * Repository for Chinese characters
 * Contains common characters suitable for children aged 3-10
 */
object CharacterRepository {
    private val characters = listOf(
        ChineseCharacter("人", "rén", "person"),
        ChineseCharacter("口", "kǒu", "mouth"),
        ChineseCharacter("手", "shǒu", "hand"),
        ChineseCharacter("日", "rì", "sun"),
        ChineseCharacter("月", "yuè", "moon"),
        ChineseCharacter("水", "shuǐ", "water"),
        ChineseCharacter("火", "huǒ", "fire"),
        ChineseCharacter("山", "shān", "mountain"),
        ChineseCharacter("石", "shí", "stone"),
        ChineseCharacter("田", "tián", "field"),
        ChineseCharacter("土", "tǔ", "earth"),
        ChineseCharacter("大", "dà", "big"),
        ChineseCharacter("小", "xiǎo", "small"),
        ChineseCharacter("上", "shàng", "up"),
        ChineseCharacter("下", "xià", "down"),
        ChineseCharacter("中", "zhōng", "middle"),
        ChineseCharacter("一", "yī", "one"),
        ChineseCharacter("二", "èr", "two"),
        ChineseCharacter("三", "sān", "three"),
        ChineseCharacter("四", "sì", "four"),
        ChineseCharacter("五", "wǔ", "five"),
        ChineseCharacter("六", "liù", "six"),
        ChineseCharacter("七", "qī", "seven"),
        ChineseCharacter("八", "bā", "eight"),
        ChineseCharacter("九", "jiǔ", "nine"),
        ChineseCharacter("十", "shí", "ten"),
        ChineseCharacter("木", "mù", "wood"),
        ChineseCharacter("林", "lín", "forest"),
        ChineseCharacter("森", "sēn", "woods"),
        ChineseCharacter("天", "tiān", "sky"),
        ChineseCharacter("地", "dì", "ground"),
        ChineseCharacter("我", "wǒ", "I/me"),
        ChineseCharacter("你", "nǐ", "you"),
        ChineseCharacter("他", "tā", "he/him"),
        ChineseCharacter("她", "tā", "she/her"),
        ChineseCharacter("好", "hǎo", "good"),
        ChineseCharacter("学", "xué", "learn"),
        ChineseCharacter("字", "zì", "character"),
        ChineseCharacter("爱", "ài", "love"),
        ChineseCharacter("家", "jiā", "home")
    )

    fun getAllCharacters(): List<ChineseCharacter> = characters

    fun getCharacter(index: Int): ChineseCharacter? {
        return if (index in characters.indices) characters[index] else null
    }

    fun getRandomCharacters(count: Int): List<ChineseCharacter> {
        return characters.shuffled().take(count)
    }

    fun getTotalCount(): Int = characters.size
}
