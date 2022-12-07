package com.example.unittestingsamplekotlin

class MainViewModelMockData {
    companion object {
        val ok = FakeResponse(
            200, """
        {
        "error": null,
        "data": "Any class"
        }
        """
        )

        val badRequest = FakeResponse(
            400, """
        {
        "error": "big error",
        "data": null
        }
        """
        )

        val okGarbage = FakeResponse(200, "{loremIpsum")
        val okNull = FakeResponse(200, """
        {
        "error": null,
        "data": null
        }
        """
        )

        val badRequestGarbage = FakeResponse(400, "{loremIpsum")
        val badRequestNull = FakeResponse(400, """
        {
        "error": null,
        "data": null
        }
        """
        )

        const val okDeserialized = "Any class"
        const val badRequestDeserialized = "big error"
        const val serverErrorDeserialized = "Server error"
        const val noInternetDeserialized = "No internet connection"
        const val unexpectedErrorDeserialized = "Unexpected error"
    }
}