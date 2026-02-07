package com.machmudow.kaszowska.sections.model

enum class Section(
    val index: Int,
    val title: String,
) {
    HERO(0, "Strona główna"),
    ABOUT(1, "O mnie"),
    SERVICES(2, "Usługi"),
    OFFICE_OFER(3, "Oferta gabinetowa"),
    TRAINING_OFER(4, "Oferta szkoleniowa"),
    PRICE_LIST(5, "Cennik"),
    CONTACT(6, "Kontakt"),
}
