package com.machmudow.kaszowska.sections.model

enum class Section(
    val index: Int,
    val title: String,
) {
    HERO(0, "Strona główna"),
    ABOUT(1, "O mnie"),
    SERVICES(2, "Usługi"),
    OFFICE_OFER(3, "Oferta gabinetowa"),  // Header(3), Cards(4)
    TRAINING_OFER(5, "Oferta szkoleniowa"),  // Header(5), Trainer(6), Curriculum(7), Package(8), Pricing(9)
    PRICE_LIST(10, "Cennik"),
    // 11 is photos
    CONTACT(12, "Kontakt"),
}
