package com.example.model

enum class Difficulty {
  BEGINNER, INTERMEDIATE, ADVANCED
}

enum class ExperimentCategory(val displayName: String, val icon: String) {
  BASIC("Basic Chemistry", "🧪"),
  INORGANIC("Inorganic Chemistry", "⚗️"),
  ORGANIC("Organic Chemistry", "🧬"),
  PHYSICAL("Physical Chemistry", "🌡️")
}

data class ExperimentStep(
  val stepNumber: Int,
  val title: String,
  val instruction: String,
  val whyExplanation: String,
  val hint: String = "",
  val actionLabel: String = "Perform Step",
  val animationType: String = "POUR", // POUR, HEAT, STIR, DROP, MEASURE, OBSERVE
  val colorShiftHex: Long = 0xFF38BDF8
)

data class Experiment(
  val id: String,
  val title: String,
  val category: ExperimentCategory,
  val subcategory: String,
  val difficulty: Difficulty,
  val timeMinutes: Int,
  val chemicals: List<String>,
  val apparatus: List<String>,
  val description: String,
  val safetyNotes: String,
  val reactionFormula: String,
  val steps: List<ExperimentStep>
)

enum class ChemicalCategory {
  ACID, BASE, SALT, INDICATOR, METAL, ORGANIC, OXIDIZER, WATER
}

data class Chemical(
  val id: String,
  val name: String,
  val formula: String,
  val category: ChemicalCategory,
  val state: String, // Liquid, Solid, Solution, Gas
  val colorHex: Long,
  val ph: Double,
  val hazardLevel: String, // Safe, Corrosive, Flammable, Toxic, Irritant
  val molarMass: Double,
  val description: String
)

data class ApparatusItem(
  val id: String,
  val name: String,
  val description: String,
  val capacityMl: Int,
  val symbol: String
)

enum class ReactionType(val label: String) {
  COMBINATION("Combination"),
  DECOMPOSITION("Decomposition"),
  DISPLACEMENT("Single Displacement"),
  DOUBLE_DISPLACEMENT("Double Displacement"),
  NEUTRALIZATION("Neutralization"),
  REDOX("Redox"),
  COMBUSTION("Combustion"),
  PRECIPITATION("Precipitation")
}

data class ChemicalReaction(
  val reactants: List<String>, // formulas e.g. ["HCl", "NaOH"]
  val products: List<String>, // formulas e.g. ["NaCl", "H2O"]
  val equation: String,
  val reactionType: ReactionType,
  val colorChange: String,
  val tempChange: String,
  val gasProduced: String? = null,
  val precipitateColor: String? = null,
  val resultingColorHex: Long = 0xFF00E5FF,
  val explanation: String,
  val safetyWarning: String
)

enum class ElementCategory(val label: String, val colorHex: Long) {
  ALKALI_METAL("Alkali Metal", 0xFFEF4444),
  ALKALINE_EARTH("Alkaline Earth", 0xFFF97316),
  TRANSITION_METAL("Transition Metal", 0xFFF59E0B),
  POST_TRANSITION("Post-transition Metal", 0xFF10B981),
  METALLOID("Metalloid", 0xFF06B6D4),
  NONMETAL("Reactive Nonmetal", 0xFF3B82F6),
  HALOGEN("Halogen", 0xFF8B5CF6),
  NOBLE_GAS("Noble Gas", 0xFFEC4899),
  LANTHANIDE("Lanthanide", 0xFFD946EF),
  ACTINIDE("Actinide", 0xFFA855F7)
}

data class ChemicalElement(
  val atomicNumber: Int,
  val symbol: String,
  val name: String,
  val atomicMass: Double,
  val electronicConfig: String,
  val valency: Int,
  val group: Int,
  val period: Int,
  val block: String,
  val category: ElementCategory,
  val meltingPointK: Double,
  val boilingPointK: Double,
  val densityGcm3: Double,
  val commonUses: String,
  val commonCompounds: String,
  val shells: List<Int>
)

data class ChemistryNote(
  val id: String,
  val title: String,
  val category: String,
  val definition: String,
  val keyFormulas: List<String>,
  val equations: List<String>,
  val examTips: List<String>,
  val simpleExplanation: String,
  val examples: List<String>,
  val quizCategory: String
)

enum class QuestionType {
  MULTIPLE_CHOICE,
  TRUE_FALSE,
  IDENTIFY_APPARATUS,
  IDENTIFY_REACTION,
  BALANCE_EQUATION
}

data class QuizQuestion(
  val id: String,
  val type: QuestionType,
  val question: String,
  val options: List<String>,
  val correctIndex: Int,
  val explanation: String,
  val category: String,
  val difficulty: Difficulty
)

data class BadgeItem(
  val id: String,
  val title: String,
  val icon: String,
  val description: String,
  val xpThreshold: Int,
  val isUnlocked: Boolean = false
)

data class DailyFact(
  val id: Int,
  val title: String,
  val fact: String,
  val icon: String
)
