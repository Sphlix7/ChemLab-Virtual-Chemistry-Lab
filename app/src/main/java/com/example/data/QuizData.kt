package com.example.data

import com.example.model.Difficulty
import com.example.model.QuestionType
import com.example.model.QuizQuestion

object QuizData {
  val questions = listOf(
    QuizQuestion(
      id = "q1",
      type = QuestionType.MULTIPLE_CHOICE,
      question = "What color does phenolphthalein indicator turn in a basic solution (pH > 8.2)?",
      options = listOf("Colorless", "Magenta Pink", "Bright Yellow", "Deep Blue"),
      correctIndex = 1,
      explanation = "Phenolphthalein is completely colorless in acidic and neutral solutions (pH < 8.2), but turns bright magenta pink in basic solutions due to structural resonance rearrangement into a quinoid dianion.",
      category = "Acids & Bases",
      difficulty = Difficulty.BEGINNER
    ),
    QuizQuestion(
      id = "q2",
      type = QuestionType.IDENTIFY_APPARATUS,
      question = "Which apparatus is designed for delivering precise, dropwise volumes of titrant during volumetric analysis?",
      options = listOf("Graduated Beaker", "Burette with Stopcock", "Conical Flask", "Measuring Cylinder"),
      correctIndex = 1,
      explanation = "A burette features calibrated precision graduation lines and a finely adjustable stopcock tap to deliver titrant drop-by-drop until the exact neutralization endpoint.",
      category = "Lab Apparatus",
      difficulty = Difficulty.BEGINNER
    ),
    QuizQuestion(
      id = "q3",
      type = QuestionType.IDENTIFY_REACTION,
      question = "What type of reaction is: Zn (s) + CuSO4 (aq) → ZnSO4 (aq) + Cu (s)?",
      options = listOf("Neutralization", "Decomposition", "Single Displacement (Redox)", "Combustion"),
      correctIndex = 2,
      explanation = "Zinc is higher in the reactivity series than copper; it loses electrons (oxidation) and displaces Cu²⁺ ions from solution, which gain electrons (reduction) to precipitate as elemental copper.",
      category = "Chemical Reactions",
      difficulty = Difficulty.BEGINNER
    ),
    QuizQuestion(
      id = "q4",
      type = QuestionType.TRUE_FALSE,
      question = "True or False: When diluting concentrated sulfuric acid, you should always pour water directly into the acid.",
      options = listOf("True", "False - Always Add Acid to Water!"),
      correctIndex = 1,
      explanation = "FALSE! Diluting concentrated acid is intensely exothermic. Pouring water onto acid causes the top layer of water to instantly boil and spit corrosive acid droplets. Always add acid slowly to a large volume of water with stirring.",
      category = "Lab Safety",
      difficulty = Difficulty.BEGINNER
    ),
    QuizQuestion(
      id = "q5",
      type = QuestionType.BALANCE_EQUATION,
      question = "What coefficients correctly balance the combustion equation: __ C3H8 + __ O2 → __ CO2 + __ H2O?",
      options = listOf("1, 5 → 3, 4", "1, 3 → 3, 2", "2, 7 → 6, 8", "1, 4 → 3, 4"),
      correctIndex = 0,
      explanation = "Balancing carbon: 3 on left requires 3 CO2. Balancing hydrogen: 8 on left requires 4 H2O (4×2=8). Total oxygens on right = (3×2) + (4×1) = 10 O atoms, requiring 5 O2 molecules. Balanced: C3H8 + 5O2 → 3CO2 + 4H2O.",
      category = "Stoichiometry",
      difficulty = Difficulty.INTERMEDIATE
    ),
    QuizQuestion(
      id = "q6",
      type = QuestionType.MULTIPLE_CHOICE,
      question = "In a flame test, which metal cation produces an intense, brilliant lilac / violet colored flame?",
      options = listOf("Sodium (Na+)", "Copper (Cu2+)", "Potassium (K+)", "Calcium (Ca2+)"),
      correctIndex = 2,
      explanation = "Potassium (K⁺) ions emit photons at 404 nm and 766 nm, producing a characteristic soft lilac/violet flame, often observed through cobalt blue glass to filter sodium contamination.",
      category = "Inorganic Chemistry",
      difficulty = Difficulty.INTERMEDIATE
    ),
    QuizQuestion(
      id = "q7",
      type = QuestionType.MULTIPLE_CHOICE,
      question = "What is the molar mass of Calcium Carbonate (CaCO3)? (Ca=40.08, C=12.01, O=16.00)",
      options = listOf("68.09 g/mol", "100.09 g/mol", "84.08 g/mol", "116.09 g/mol"),
      correctIndex = 1,
      explanation = "CaCO3 = 1×40.08 + 1×12.01 + 3×16.00 = 40.08 + 12.01 + 48.00 = 100.09 g/mol.",
      category = "Stoichiometry",
      difficulty = Difficulty.BEGINNER
    ),
    QuizQuestion(
      id = "q8",
      type = QuestionType.MULTIPLE_CHOICE,
      question = "What functional group characterizes an ester?",
      options = listOf("-OH (Hydroxyl)", "-CHO (Formyl)", "-COO- (Carboxylate ester)", "-NH2 (Amino)"),
      correctIndex = 2,
      explanation = "Esters are derived from a carboxylic acid and an alcohol, containing the functional linkage -COO- (a carbonyl adjacent to an alkoxy oxygen), known for pleasant fruit and flower fragrances.",
      category = "Organic Chemistry",
      difficulty = Difficulty.INTERMEDIATE
    ),
    QuizQuestion(
      id = "q9",
      type = QuestionType.IDENTIFY_REACTION,
      question = "Mixing Lead Nitrate Pb(NO3)2 and Potassium Iodide KI produces a brilliant yellow solid. What reaction type is this?",
      options = listOf("Combustion", "Precipitation (Double Displacement)", "Neutralization", "Thermal Decomposition"),
      correctIndex = 1,
      explanation = "Pb(NO3)2 (aq) + 2KI (aq) → PbI2 (s)↓ + 2KNO3 (aq) is a precipitation double displacement reaction where insoluble yellow Lead(II) Iodide falls out of solution.",
      category = "Chemical Reactions",
      difficulty = Difficulty.BEGINNER
    ),
    QuizQuestion(
      id = "q10",
      type = QuestionType.TRUE_FALSE,
      question = "True or False: A solution with a pH of 3 is twice as acidic as a solution with a pH of 6.",
      options = listOf("True", "False - It is 1000 times more acidic!"),
      correctIndex = 1,
      explanation = "FALSE! The pH scale is logarithmic (base 10). Each unit represents a 10-fold change in [H⁺] concentration. ΔpH = 6 - 3 = 3 units, so 10³ = 1,000 times higher hydrogen ion concentration!",
      category = "Acids & Bases",
      difficulty = Difficulty.INTERMEDIATE
    )
  )
}
