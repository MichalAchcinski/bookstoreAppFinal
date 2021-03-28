package pl.achcinski.bookstore.model.book;

public enum Category {
    BELETRYSTYKA("Beletrystyka"),
    LITERATURA_FAKTU_PUBLICYSTYKA("Literatura faktu, publicystyka"),
    LITERATURA_POPULARNONAUKOWA("Literatura popularnonaukowa"),
    LITERATURA_DZIEDZIECA("Literatura dziecięca"),
    KOMIKSY("Komiksy"),
    POEZJA_DRAMAT_SATYRA("Poezja, dramat, satyra"),
    SCIENCE_FICTION("Sciene fiction"),
    BIOGRAFIA("Biografia"),
    POZOSTALE("Pozostałe");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
