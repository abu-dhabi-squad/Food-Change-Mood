package logic.usecase

import logic.repository.FoodRepository
import model.CountryMealsFetchException
import model.EmptyListException
import model.Food


class GetRandomMealsByCountryUseCase(
    private val foodRepository: FoodRepository
) {

    fun getRandomMeals(country: String): List<String> {

        return foodRepository
            .getFoods()
            .getOrThrow()
            .filter { meal -> meal.matchesCountry(getRelatedCuisineKeywords(country)) }
            .takeIf { meals -> meals.isNotEmpty() }
            ?.distinctBy { it.name?.lowercase()?.trim() }
            ?.mapNotNull { meal -> meal.name }
            ?.shuffled()
            ?.take(MAXIMUM_NUMBER_OF_MEALS)
            ?.sorted()
            ?: throw EmptyListException()
    }

    private fun Food.matchesCountry(relatedWords: List<String>): Boolean {

        val lowerName = name?.lowercase().orEmpty()
        val lowerDesc = description?.lowercase().orEmpty()
        val lowerTags = tags.map { it.lowercase() }

        return relatedWords.any { word ->
            val regex = Regex("\\b${Regex.escape(word)}\\b", RegexOption.IGNORE_CASE)
            regex.containsMatchIn(lowerName) ||
                    regex.containsMatchIn(lowerDesc) ||
                    lowerTags.any { tag -> regex.containsMatchIn(tag) }
        }
    }


    private fun getRelatedCuisineKeywords(country: String): List<String> {

        val countryLowercase = country.lowercase().trim()
        return CUISINE_KEYWORDS_BY_COUNTRY[countryLowercase] ?: throw CountryMealsFetchException()
    }

    companion object{

        const val MAXIMUM_NUMBER_OF_MEALS = 20
        val CUISINE_KEYWORDS_BY_COUNTRY = mapOf(
            "afghanistan" to listOf("afghan", "afghani", "asian"),
            "albania" to listOf("albanian", "european"),
            "algeria" to listOf("algerian", "african"),
            "andorra" to listOf("andorran", "european"),
            "angola" to listOf("angolan", "african"),
            "antigua and barbuda" to listOf("north american"),
            "argentina" to listOf("argentinian", "argentine", "south american"),
            "armenia" to listOf("armenian", "asian"),
            "australia" to listOf("australian", "aussie", "oceanian"),
            "austria" to listOf("austrian", "european"),
            "azerbaijan" to listOf("azerbaijani", "asian"),
            "bahamas" to listOf("bahamian", "north american"),
            "bahrain" to listOf("bahraini", "asian"),
            "bangladesh" to listOf("bangladeshi", "asian"),
            "barbados" to listOf("barbadian", "north american"),
            "belarus" to listOf("belarusian", "european"),
            "belgium" to listOf("belgian", "european"),
            "belize" to listOf("belizean", "north american"),
            "benin" to listOf("beninese", "african"),
            "bhutan" to listOf("bhutanese", "asian"),
            "bolivia" to listOf("bolivian", "south american"),
            "bosnia and herzegovina" to listOf("bosnian", "herzegovinian", "european"),
            "botswana" to listOf("botswanan", "african"),
            "brazil" to listOf("brazilian", "brasil", "south american"),
            "brunei" to listOf("bruneian", "asian"),
            "bulgaria" to listOf("bulgarian", "european"),
            "burkina faso" to listOf("burkinabe", "african"),
            "burundi" to listOf("burundian", "african"),
            "cabo verde" to listOf("cape verdean", "african"),
            "cambodia" to listOf("cambodian", "khmer", "asian"),
            "cameroon" to listOf("cameroonian", "african"),
            "canada" to listOf("canadian", "north american"),
            "central african republic" to listOf("african"),
            "chad" to listOf("chadian", "african"),
            "chile" to listOf("chilean", "south american"),
            "china" to listOf("chinese", "prc", "asian"),
            "colombia" to listOf("colombian", "south american"),
            "comoros" to listOf("comorian", "african"),
            "congo (brazzaville)" to listOf("congolese", "african"),
            "congo (kinshasa)" to listOf("congolese", "african"),
            "costa rica" to listOf("costa rican", "north american"),
            "croatia" to listOf("croatian", "european"),
            "cuba" to listOf("cuban", "north american"),
            "cyprus" to listOf("cypriot", "asian"),
            "czech republic" to listOf("czech", "european"),
            "denmark" to listOf("danish", "european"),
            "djibouti" to listOf("djiboutian", "african"),
            "dominica" to listOf("north american"),
            "dominican republic" to listOf("dominican", "north american"),
            "ecuador" to listOf("ecuadorean", "south american"),
            "egypt" to listOf("egyptian", "african"),
            "el salvador" to listOf("salvadoran", "north american"),
            "equatorial guinea" to listOf("african"),
            "eritrea" to listOf("eritrean", "african"),
            "estonia" to listOf("estonian", "european"),
            "eswatini" to listOf("swazi", "african"),
            "ethiopia" to listOf("ethiopian", "african"),
            "fiji" to listOf("fijian", "oceanian"),
            "finland" to listOf("finnish", "european"),
            "france" to listOf("french", "française", "european"),
            "gabon" to listOf("gabonese", "african"),
            "gambia" to listOf("gambian", "african"),
            "georgia" to listOf("georgian", "asian"),
            "germany" to listOf("german", "deutsch", "european"),
            "ghana" to listOf("ghanaian", "african"),
            "greece" to listOf("greek", "hellenic", "european"),
            "grenada" to listOf("grenadian", "north american"),
            "guatemala" to listOf("guatemalan", "north american"),
            "guinea" to listOf("guinean", "african"),
            "guinea-bissau" to listOf("african"),
            "guyana" to listOf("guyanese", "south american"),
            "haiti" to listOf("haitian", "north american"),
            "honduras" to listOf("honduran", "north american"),
            "hungary" to listOf("hungarian", "european"),
            "iceland" to listOf("icelandic", "european"),
            "india" to listOf("indian", "bharat", "asian"),
            "indonesia" to listOf("indonesian", "asian"),
            "iran" to listOf("iranian", "persian", "asian"),
            "iraq" to listOf("iraqi", "asian"),
            "ireland" to listOf("irish", "european"),
            "israel" to listOf("israeli", "asian"),
            "italy" to listOf("italian", "italia", "european"),
            "jamaica" to listOf("jamaican", "north american"),
            "japan" to listOf("japanese", "nippon", "asian"),
            "jordan" to listOf("jordanian", "asian"),
            "kazakhstan" to listOf("kazakh", "asian"),
            "kenya" to listOf("kenyan", "african"),
            "kiribati" to listOf("oceanian"),
            "korea, north" to listOf("korean", "north korea", "asian"),
            "korea, south" to listOf("korean", "south korea", "asian"),
            "kosovo" to listOf("european"),
            "kuwait" to listOf("kuwaiti", "asian"),
            "kyrgyzstan" to listOf("kyrgyz", "asian"),
            "laos" to listOf("laotian", "asian"),
            "latvia" to listOf("latvian", "european"),
            "lebanon" to listOf("lebanese", "asian"),
            "lesotho" to listOf("basotho", "african"),
            "liberia" to listOf("liberian", "african"),
            "libya" to listOf("libyan", "african"),
            "liechtenstein" to listOf("european"),
            "lithuania" to listOf("lithuanian", "european"),
            "luxembourg" to listOf("luxembourgish", "european"),
            "madagascar" to listOf("malagasy", "african"),
            "malawi" to listOf("malawian", "african"),
            "malaysia" to listOf("malaysian", "asian"),
            "maldives" to listOf("maldivian", "asian"),
            "mali" to listOf("malian", "african"),
            "malta" to listOf("maltese", "european"),
            "marshall islands" to listOf("oceanian"),
            "mauritania" to listOf("mauritanian", "african"),
            "mauritius" to listOf("mauritian", "african"),
            "mexico" to listOf("mexican", "méxico", "north american"),
            "micronesia" to listOf("oceanian"),
            "moldova" to listOf("moldovan", "european"),
            "monaco" to listOf("monégasque", "european"),
            "mongolia" to listOf("mongolian", "asian"),
            "montenegro" to listOf("montenegrin", "european"),
            "morocco" to listOf("moroccan", "african"),
            "mozambique" to listOf("mozambican", "african"),
            "myanmar" to listOf("burmese", "asian"),
            "namibia" to listOf("namibian", "african"),
            "nauru" to listOf("oceanian"),
            "nepal" to listOf("nepali", "asian"),
            "netherlands" to listOf("dutch", "european"),
            "new zealand" to listOf("kiwi", "new zealand", "oceanian"),
            "nicaragua" to listOf("nicaraguan", "north american"),
            "niger" to listOf("nigerien", "african"),
            "nigeria" to listOf("nigerian", "african"),
            "north macedonia" to listOf("macedonian", "european"),
            "norway" to listOf("norwegian", "european"),
            "oman" to listOf("omani", "asian"),
            "pakistan" to listOf("pakistani", "asian"),
            "palau" to listOf("oceanian"),
            "palestine" to listOf("palestinian", "asian"),
            "panama" to listOf("panamanian", "north american"),
            "papua new guinea" to listOf("oceanian"),
            "paraguay" to listOf("paraguayan", "south american"),
            "peru" to listOf("peruvian", "south american"),
            "philippines" to listOf("filipino", "philippine", "asian"),
            "poland" to listOf("polish", "european"),
            "portugal" to listOf("portuguese", "european"),
            "qatar" to listOf("qatari", "asian"),
            "romania" to listOf("romanian", "european"),
            "russia" to listOf("russian", "россия", "european", "asian"),
            "rwanda" to listOf("rwandan", "african"),
            "saint kitts and nevis" to listOf("north american"),
            "saint lucia" to listOf("north american"),
            "saint vincent and the grenadines" to listOf("north american"),
            "samoa" to listOf("samoan", "oceanian"),
            "san marino" to listOf("european"),
            "sao tome and principe" to listOf("african"),
            "saudi arabia" to listOf("saudi", "arabian", "asian"),
            "senegal" to listOf("senegalese", "african"),
            "serbia" to listOf("serbian", "european"),
            "seychelles" to listOf("seychellois", "african"),
            "sierra leone" to listOf("sierran", "african"),
            "singapore" to listOf("singaporean", "asian"),
            "slovakia" to listOf("slovak", "european"),
            "slovenia" to listOf("slovenian", "european"),
            "solomon islands" to listOf("oceanian"),
            "somalia" to listOf("somali", "african"),
            "south africa" to listOf("south african", "african"),
            "south sudan" to listOf("south sudanese", "african"),
            "spain" to listOf("spanish", "españa", "european"),
            "sri lanka" to listOf("sri lankan", "asian"),
            "sudan" to listOf("sudanese", "african"),
            "suriname" to listOf("surinamese", "south american"),
            "sweden" to listOf("swedish", "european"),
            "switzerland" to listOf("swiss", "european"),
            "syria" to listOf("syrian", "asian"),
            "taiwan" to listOf("taiwanese", "asian"),
            "tajikistan" to listOf("tajik", "asian"),
            "tanzania" to listOf("tanzanian", "african"),
            "thailand" to listOf("thai", "asian"),
            "timor-leste" to listOf("asian"),
            "togo" to listOf("togolese", "african"),
            "tonga" to listOf("tongan", "oceanian"),
            "trinidad and tobago" to listOf("trinidadian", "tobagonian", "north american"),
            "tunisia" to listOf("tunisian", "african"),
            "turkey" to listOf("turkish", "türkiye", "asian", "european"),
            "turkmenistan" to listOf("turkmen", "asian"),
            "tuvalu" to listOf("oceanian"),
            "uganda" to listOf("ugandan", "african"),
            "ukraine" to listOf("ukrainian", "european"),
            "united arab emirates" to listOf("emirati", "asian"),
            "united kingdom" to listOf("british", "uk", "english", "european"),
            "united states" to listOf("american", "usa", "us", "america", "north american"),
            "uruguay" to listOf("uruguayan", "south american"),
            "uzbekistan" to listOf("uzbek", "asian"),
            "vanuatu" to listOf("ni-vanuatu", "oceanian"),
            "vatican city" to listOf("vatican", "holy see", "european"),
            "venezuela" to listOf("venezuelan", "south american"),
            "vietnam" to listOf("vietnamese", "viet", "asian"),
            "yemen" to listOf("yemeni", "asian"),
            "zambia" to listOf("zambian", "african"),
            "zimbabwe" to listOf("zimbabwean", "african")
        )
    }
}