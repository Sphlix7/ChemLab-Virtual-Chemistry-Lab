package com.example.data

import com.example.model.Difficulty
import com.example.model.Experiment
import com.example.model.ExperimentCategory
import com.example.model.ExperimentStep

object ExperimentData {
  val experiments = listOf(
    // Basic Chemistry
    Experiment(
      id = "exp_1",
      title = "Acid-Base Neutralization & Titration",
      category = ExperimentCategory.BASIC,
      subcategory = "Neutralization",
      difficulty = Difficulty.BEGINNER,
      timeMinutes = 15,
      chemicals = listOf("HCl (0.1 M)", "NaOH (0.1 M)", "Phenolphthalein Indicator", "Distilled Water"),
      apparatus = listOf("Burette", "Conical Flask", "Measuring Cylinder", "Dropper", "Pipette"),
      description = "Determine the exact neutralization endpoint between strong acid and strong base using phenolphthalein indicator.",
      safetyNotes = "Wear protective safety goggles and nitrile gloves. Acids and bases cause irritation.",
      reactionFormula = "HCl + NaOH → NaCl + H2O",
      steps = listOf(
        ExperimentStep(
          stepNumber = 1,
          title = "Prepare Flask & Pipette Sample",
          instruction = "Measure 25.0 mL of 0.1 M Hydrochloric Acid (HCl) using a graduated pipette and transfer into a clean conical flask.",
          whyExplanation = "Conical flasks have a narrow tapered neck which allows vigorous swirling without spilling or splashing acidic droplets.",
          hint = "Pipette from eye level to accurately align the bottom of the meniscus with the calibration line.",
          actionLabel = "Pipette 25 mL HCl",
          animationType = "POUR",
          colorShiftHex = 0xAAECFEFF
        ),
        ExperimentStep(
          stepNumber = 2,
          title = "Add Indicator Drops",
          instruction = "Add 2 to 3 drops of Phenolphthalein indicator solution into the conical flask.",
          whyExplanation = "Phenolphthalein is completely colorless in acidic and neutral solutions (pH < 8.2), making the transition to pink sharp and distinct.",
          hint = "Do not add too much indicator; indicators are weak organic acids themselves and excess drops shift the endpoint.",
          actionLabel = "Add 2 Drops Phenolphthalein",
          animationType = "DROP",
          colorShiftHex = 0x88ECFEFF
        ),
        ExperimentStep(
          stepNumber = 3,
          title = "Fill & Zero the Burette",
          instruction = "Fill the burette with standardized 0.1 M Sodium Hydroxide (NaOH) and ensure no air bubbles are trapped in the stopcock tip.",
          whyExplanation = "Trapped air bubbles below the stopcock will escape during titration, causing an overestimation of the titrant volume consumed.",
          hint = "Open the stopcock briefly to flush liquid through the tip before recording the initial zero reading.",
          actionLabel = "Fill Burette with NaOH",
          animationType = "POUR",
          colorShiftHex = 0x88D0E5FF
        ),
        ExperimentStep(
          stepNumber = 4,
          title = "Titrate with Swirling",
          instruction = "Carefully open the stopcock to deliver NaOH into the flask while continuously swirling in a circular motion.",
          whyExplanation = "Continuous swirling ensures homogeneous mixing so localized excess titrant reacts immediately with available hydronium ions.",
          hint = "Slow down to drop-by-drop addition as temporary flashes of pink take longer to dissipate.",
          actionLabel = "Dispense Titrant Dropwise",
          animationType = "STIR",
          colorShiftHex = 0xAAFBCFE8
        ),
        ExperimentStep(
          stepNumber = 5,
          title = "Detect Endpoint & Record Reading",
          instruction = "Stop adding titrant the moment a permanent, faint pale pink tint persists for at least 30 seconds.",
          whyExplanation = "At this stoichiometric equivalence point (pH ≈ 7.0–8.5), exactly 1 mole of OH⁻ has reacted with 1 mole of H⁺.",
          hint = "A faint blush pink indicates true equivalence; dark hot magenta means over-titration.",
          actionLabel = "Record Burette Volume & Calculate",
          animationType = "MEASURE",
          colorShiftHex = 0xFFF472B6
        )
      )
    ),

    Experiment(
      id = "exp_2",
      title = "Golden Rain: Lead Iodide Precipitation",
      category = ExperimentCategory.INORGANIC,
      subcategory = "Precipitation reactions",
      difficulty = Difficulty.INTERMEDIATE,
      timeMinutes = 20,
      chemicals = listOf("Lead(II) Nitrate Pb(NO3)2", "Potassium Iodide KI", "Distilled Water"),
      apparatus = listOf("Beaker 250mL", "Test Tubes", "Bunsen Burner", "Tripod Stand", "Dropper"),
      description = "Synthesize shimmering golden hexagonal crystals of Lead(II) Iodide that drift like gold glitter upon slow cooling.",
      safetyNotes = "Lead is a toxic heavy metal. Virtual simulation allows safe exploration of this historic demonstration.",
      reactionFormula = "Pb(NO3)2 + 2KI → PbI2↓ + 2KNO3",
      steps = listOf(
        ExperimentStep(
          stepNumber = 1,
          title = "Dissolve Reactants",
          instruction = "Dissolve 0.5g Lead Nitrate in 50 mL warm water in Beaker A, and 1.0g Potassium Iodide in Beaker B.",
          whyExplanation = "Dissolving crystalline salts fully dissociates them into free solvated Pb²⁺, NO₃⁻, K⁺, and I⁻ ions ready for collision.",
          hint = "Both starting solutions are completely transparent and colorless.",
          actionLabel = "Dissolve Salts",
          animationType = "POUR",
          colorShiftHex = 0xAAECFEFF
        ),
        ExperimentStep(
          stepNumber = 2,
          title = "Mix Solutions",
          instruction = "Pour the Potassium Iodide solution into the Lead Nitrate beaker.",
          whyExplanation = "Double displacement occurs: Pb²⁺ and I⁻ ions collide and exceed the solubility limit, forming opaque yellow PbI2 precipitate.",
          hint = "Notice the instantaneous transition from two water-clear liquids into vibrant canary yellow.",
          actionLabel = "Combine Solutions",
          animationType = "POUR",
          colorShiftHex = 0xFFFACC15
        ),
        ExperimentStep(
          stepNumber = 3,
          title = "Heat Mixture to Boiling",
          instruction = "Place the beaker on the tripod and ignite the Bunsen burner. Heat until the yellow solid completely redissolves.",
          whyExplanation = "Lead(II) Iodide has temperature-dependent solubility: it dissolves substantially in near-boiling water (4.2 g/L at 100°C vs 0.7 g/L at 20°C).",
          hint = "The liquid becomes crystal clear again at boiling temperatures.",
          actionLabel = "Heat with Bunsen Burner",
          animationType = "HEAT",
          colorShiftHex = 0xEEFEF08A
        ),
        ExperimentStep(
          stepNumber = 4,
          title = "Slow Cooling & Recrystallization",
          instruction = "Turn off the flame and allow the beaker to cool undisturbed to room temperature.",
          whyExplanation = "Slow undisturbed cooling permits ordered nucleation, growing microscopic hexagonal platelet crystals that reflect light like pure gold glitter.",
          hint = "Observe the golden particles swirling in convective currents as the temperature drops.",
          actionLabel = "Observe Golden Rain",
          animationType = "OBSERVE",
          colorShiftHex = 0xFFEAB308
        )
      )
    ),

    Experiment(
      id = "exp_3",
      title = "Fischer Esterification: Fruity Esters",
      category = ExperimentCategory.ORGANIC,
      subcategory = "Esterification",
      difficulty = Difficulty.INTERMEDIATE,
      timeMinutes = 25,
      chemicals = listOf("Acetic Acid (Glacial)", "Ethanol (95%)", "Concentrated Sulfuric Acid H2SO4", "Sodium Carbonate Solution"),
      apparatus = listOf("Test Tube", "Water Bath Beaker", "Bunsen Burner", "Dropper", "Thermometer"),
      description = "Synthesize ethyl ethanoate (ethyl acetate) with pleasant sweet pear aroma via acid-catalyzed condensation.",
      safetyNotes = "Glacial acetic acid and concentrated sulfuric acid are highly corrosive. Alcohols are flammable.",
      reactionFormula = "CH3COOH + C2H5OH ⇌ CH3COOC2H5 + H2O",
      steps = listOf(
        ExperimentStep(
          stepNumber = 1,
          title = "Combine Carboxylic Acid & Alcohol",
          instruction = "Add 3 mL of glacial acetic acid and 3 mL of ethanol into a clean dry test tube.",
          whyExplanation = "Equal stoichiometric proportions of carboxylic acid and alcohol provide balanced reactants for equilibrium synthesis.",
          hint = "Ensure test tube is completely dry, as water pushes equilibrium backward via Le Chatelier's principle.",
          actionLabel = "Add Organic Reactants",
          animationType = "POUR",
          colorShiftHex = 0x88ECFEFF
        ),
        ExperimentStep(
          stepNumber = 2,
          title = "Add Acid Catalyst",
          instruction = "Carefully add 4 drops of concentrated sulfuric acid (H2SO4) down the wall of the tube.",
          whyExplanation = "H2SO4 acts as both a proton catalyst (activating the carbonyl carbon for nucleophilic attack) and a dehydrating agent absorbing water.",
          hint = "Add drop by drop to prevent localized overheating.",
          actionLabel = "Add Catalytic H2SO4",
          animationType = "DROP",
          colorShiftHex = 0x99F0FDFA
        ),
        ExperimentStep(
          stepNumber = 3,
          title = "Heat in Warm Water Bath",
          instruction = "Immerse test tube in a beaker of water maintained at 60°C for 10 minutes.",
          whyExplanation = "Heating accelerates the reaction rate towards equilibrium. A water bath is used because organic vapors are highly flammable over direct flames.",
          hint = "Do not allow water bath to boil violently.",
          actionLabel = "Warm in Water Bath (60°C)",
          animationType = "HEAT",
          colorShiftHex = 0xAAFDE047
        ),
        ExperimentStep(
          stepNumber = 4,
          title = "Isolate Ester & Smell Aroma",
          instruction = "Pour the warm contents into a beaker of dilute sodium carbonate solution.",
          whyExplanation = "Sodium carbonate neutralizes unreacted acetic acid and sulfuric acid into water-soluble salts, allowing the sweet insoluble ester layer to float on top.",
          hint = "Gently waft the vapor towards your nose; smell sweet apple/pear notes.",
          actionLabel = "Neutralize & Detect Ester Scent",
          animationType = "OBSERVE",
          colorShiftHex = 0xFFFACC15
        )
      )
    ),

    Experiment(
      id = "exp_4",
      title = "Metal Flame Tests for Cations",
      category = ExperimentCategory.INORGANIC,
      subcategory = "Flame tests",
      difficulty = Difficulty.BEGINNER,
      timeMinutes = 15,
      chemicals = listOf("Sodium Chloride (Na+)", "Potassium Chloride (K+)", "Copper(II) Sulfate (Cu2+)", "Calcium Chloride (Ca2+)", "HCl (conc)"),
      apparatus = listOf("Bunsen Burner", "Platinum/Nichrome Wire Loop", "Watch Glass", "Safety Goggles"),
      description = "Identify unknown metal cations by observing distinctive emission colors produced when excited valence electrons drop energy levels.",
      safetyNotes = "Open flame hazard. Tie back hair and wear goggles.",
      reactionFormula = "M+(s) + heat → M*(excited) → M+(ground) + hν (photon)",
      steps = listOf(
        ExperimentStep(
          stepNumber = 1,
          title = "Clean the Wire Loop",
          instruction = "Dip nichrome wire into concentrated HCl and heat in the hottest non-luminous blue flame until no color is imparted.",
          whyExplanation = "HCl converts surface impurities to volatile metal chlorides that vaporize away, ensuring a pure uncontaminated flame baseline.",
          hint = "The flame should remain pure pale blue before taking any test sample.",
          actionLabel = "Clean Wire in Flame",
          animationType = "HEAT",
          colorShiftHex = 0xFF38BDF8
        ),
        ExperimentStep(
          stepNumber = 2,
          title = "Test Sodium (Na+)",
          instruction = "Touch wire to Sodium Chloride and place in flame. Observe bright persistent yellow-orange flame.",
          whyExplanation = "Sodium valence electrons emit intense photons at 589 nm (D-line doublet) as 3p electrons relax to the 3s ground state.",
          hint = "Sodium flame is extremely sensitive and will overpower other colors if present as a contaminant.",
          actionLabel = "Burn Sodium Sample",
          animationType = "HEAT",
          colorShiftHex = 0xFFF59E0B
        ),
        ExperimentStep(
          stepNumber = 3,
          title = "Test Copper (Cu2+)",
          instruction = "Clean wire, dip in Copper(II) salt, and introduce to flame. Observe vivid emerald blue-green flame.",
          whyExplanation = "Excited copper atoms emit light strongly across the 510-530 nm green-cyan spectral region.",
          hint = "Copper creates intense fireworks-like green sparks.",
          actionLabel = "Burn Copper Sample",
          animationType = "HEAT",
          colorShiftHex = 0xFF10B981
        ),
        ExperimentStep(
          stepNumber = 4,
          title = "Test Potassium (K+)",
          instruction = "Test Potassium Chloride. Observe delicate lilac/violet flame (viewed through cobalt blue glass).",
          whyExplanation = "Potassium emits violet photons at 404 nm and 766 nm; cobalt blue glass absorbs any yellow sodium interference.",
          hint = "The lilac tint is transient, so watch closely as the loop enters the flame edge.",
          actionLabel = "Burn Potassium Sample",
          animationType = "HEAT",
          colorShiftHex = 0xFFA855F7
        )
      )
    ),

    Experiment(
      id = "exp_5",
      title = "Investigating Factors Affecting Reaction Rates",
      category = ExperimentCategory.PHYSICAL,
      subcategory = "Rate of reaction",
      difficulty = Difficulty.INTERMEDIATE,
      timeMinutes = 20,
      chemicals = listOf("Hydrochloric Acid (0.5M, 1.0M, 2.0M)", "Magnesium Ribbon Strips (3cm)", "Water"),
      apparatus = listOf("Conical Flask", "Gas Syringe / Delivery Tube", "Stopwatch", "Thermometer", "Measuring Cylinder"),
      description = "Measure the volume of hydrogen gas evolved over time to quantify how concentration and temperature alter reaction rates.",
      safetyNotes = "Hydrogen gas generated is flammable. Acid is corrosive.",
      reactionFormula = "Mg (s) + 2HCl (aq) → MgCl2 (aq) + H2 (g)↑",
      steps = listOf(
        ExperimentStep(
          stepNumber = 1,
          title = "Set Up Apparatus & Connect Syringe",
          instruction = "Clamp gas syringe horizontally and connect delivery tube tightly to a 100 mL conical flask stopper.",
          whyExplanation = "A gas syringe enables continuous quantitative measurement of gas displacement volume without hydrostatic pressure error.",
          hint = "Ensure plunger moves freely and lubricate lightly if sticking.",
          actionLabel = "Assemble Gas Syringe",
          animationType = "MEASURE",
          colorShiftHex = 0xAAECFEFF
        ),
        ExperimentStep(
          stepNumber = 2,
          title = "Add 1.0 M Acid",
          instruction = "Pour 25 mL of 1.0 M HCl into the flask at room temperature (22°C).",
          whyExplanation = "Standardizing reactant volume ensures differences in gas evolution reflect reaction kinetics rather than limiting reagent variations.",
          hint = "Record the starting temperature before dropping the metal.",
          actionLabel = "Add 1.0 M HCl",
          animationType = "POUR",
          colorShiftHex = 0xAAECFEFF
        ),
        ExperimentStep(
          stepNumber = 3,
          title = "Drop Magnesium & Start Stopwatch",
          instruction = "Drop 3.0 cm polished magnesium ribbon into the acid, immediately replace stopper, and start stopwatch.",
          whyExplanation = "Collision theory: More frequent effective collisions between H⁺ ions and metal surface per unit time produce higher initial rates.",
          hint = "Swirl gently to prevent hydrogen bubble blankets from insulating the metal surface.",
          actionLabel = "Start Reaction & Timer",
          animationType = "STIR",
          colorShiftHex = 0x6638BDF8
        ),
        ExperimentStep(
          stepNumber = 4,
          title = "Record Volumes Every 10 Seconds",
          instruction = "Log gas syringe volume every 10 seconds until gas production ceases (curve flattens).",
          whyExplanation = "The slope of the curve (ΔV / Δt) represents instantaneous reaction rate, which decreases as reactant concentrations deplete.",
          hint = "Plot Volume (mL) vs Time (s) to visualize initial rate vs reaction completion.",
          actionLabel = "Record Rate Data Curve",
          animationType = "OBSERVE",
          colorShiftHex = 0xFF0284C7
        )
      )
    ),

    Experiment(
      id = "exp_6",
      title = "Preparation of Insoluble Salt by Precipitation",
      category = ExperimentCategory.INORGANIC,
      subcategory = "Preparation of salts",
      difficulty = Difficulty.BEGINNER,
      timeMinutes = 15,
      chemicals = listOf("Barium Chloride BaCl2", "Sodium Sulfate Na2SO4", "Distilled Water"),
      apparatus = listOf("Beaker", "Filter Funnel", "Filter Paper", "Conical Flask", "Glass Stirring Rod"),
      description = "Prepare pure dry Barium Sulfate (BaSO4) by mixing two soluble salts, followed by filtration, washing, and drying.",
      safetyNotes = "Barium chloride is toxic; wear gloves. Insoluble BaSO4 formed is completely non-toxic.",
      reactionFormula = "BaCl2 (aq) + Na2SO4 (aq) → BaSO4 (s)↓ + 2NaCl (aq)",
      steps = listOf(
        ExperimentStep(
          stepNumber = 1,
          title = "Mix Soluble Precursors",
          instruction = "Mix 25 mL of 0.2 M BaCl2 with 25 mL of 0.2 M Na2SO4 in a beaker and stir with glass rod.",
          whyExplanation = "Ba²⁺ and SO₄²⁻ ions have high lattice energy in crystalline form, driving immediate precipitation of insoluble white BaSO4.",
          hint = "The solution turns thick and milky white immediately.",
          actionLabel = "Precipitate Barium Sulfate",
          animationType = "POUR",
          colorShiftHex = 0xFFF1F5F9
        ),
        ExperimentStep(
          stepNumber = 2,
          title = "Fold Filter Paper & Filter",
          instruction = "Fold cone filter paper into funnel, moisten with water, and pour suspension down glass rod.",
          whyExplanation = "Filtration separates solid residue BaSO4 from aqueous filtrate containing soluble spectator NaCl ions.",
          hint = "Direct the stream against the side of the filter cone to avoid piercing the paper tip.",
          actionLabel = "Filter White Precipitate",
          animationType = "MEASURE",
          colorShiftHex = 0xAAFFFFFF
        ),
        ExperimentStep(
          stepNumber = 3,
          title = "Wash with Distilled Water",
          instruction = "Rinse the residue on the filter paper with several portions of distilled water.",
          whyExplanation = "Washing removes remaining traces of soluble sodium chloride and excess barium ions from the crystals.",
          hint = "Test washings with AgNO3 to confirm no chloride ions remain.",
          actionLabel = "Wash Residue",
          animationType = "DROP",
          colorShiftHex = 0x66E0F2FE
        ),
        ExperimentStep(
          stepNumber = 4,
          title = "Dry Pure Salt",
          instruction = "Transfer filter paper to a watch glass and place in warm drying oven (80°C).",
          whyExplanation = "Evaporating all absorbed water leaves pure, anhydrous crystalline Barium Sulfate powder ready for analytical weighing.",
          hint = "Allow to cool in a desiccator before final mass measurement.",
          actionLabel = "Dry & Weigh Pure BaSO4",
          animationType = "HEAT",
          colorShiftHex = 0xFFFFFFFF
        )
      )
    )
  )
}
