package learn.mockito.p1foundation.s2astro

class KFakeGateway : KGateway<KAstroResponse> {
    override fun getResponse(): KAstroResponse {
        return KAstroResponse(
            number = 7,
            message = "Success",
            people = listOf(
                KAssignment("Kathryn Janeway", "USS Voyager"),
                KAssignment("Seven of Nine", "USS Voyager"),
                KAssignment("Will Robinson", "Jupiter 2"),
                KAssignment("Lennier", "Babylon 5"),
                KAssignment("James Holden", "Rocinante"),
                KAssignment("Naomi Negata", "Rocinante"),
                KAssignment("Ellen Ripley", "Nostromo"),
            )
        )
    }
}
