package core.model.header

enum class Encoding(val value: String) {
    UTF_8("UTF-8"),
    UTF_16("UTF-16"),
    ISO_10646_UCS_2("ISO-10646-UCS-2"),
    ISO_10646_UCS_4("ISO-10646-UCS-4"),
    ISO_8859_1("ISO-8859-1"),
    ISO_8859_2("ISO-8859-2"),
    ISO_8859_3("ISO-8859-3"),
    ISO_8859_4("ISO-8859-4"),
    ISO_8859_5("ISO-8859-5"),
    ISO_8859_6("ISO-8859-6"),
    ISO_8859_7("ISO-8859-7"),
    ISO_8859_8("ISO-8859-8"),
    ISO_8859_9("ISO-8859-9"),
    ISO_2022_JP("ISO-2022-JP"),
    Shift_JIS("Shift_JIS"),
    EUC_JP("EUC-JP"),
}