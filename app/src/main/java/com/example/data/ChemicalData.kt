package com.example.data

import com.example.model.ApparatusItem
import com.example.model.Chemical
import com.example.model.ChemicalCategory

object ChemicalData {
  val chemicals = listOf(
    Chemical(
      id = "hcl",
      name = "Hydrochloric Acid",
      formula = "HCl",
      category = ChemicalCategory.ACID,
      state = "Liquid",
      colorHex = 0xAAECFEFF,
      ph = 1.0,
      hazardLevel = "Corrosive",
      molarMass = 36.46,
      description = "Strong monoprotic mineral acid with pungent vapor. Widely used in titrations and salt preparation."
    ),
    Chemical(
      id = "naoh",
      name = "Sodium Hydroxide",
      formula = "NaOH",
      category = ChemicalCategory.BASE,
      state = "Solution",
      colorHex = 0xAADBEAFE,
      ph = 13.5,
      hazardLevel = "Corrosive",
      molarMass = 40.00,
      description = "Caustic soda. Highly alkaline solution that neutralizes acids with vigorous heat generation."
    ),
    Chemical(
      id = "h2so4",
      name = "Sulfuric Acid",
      formula = "H2SO4",
      category = ChemicalCategory.ACID,
      state = "Liquid",
      colorHex = 0xAAF0FDFA,
      ph = 0.5,
      hazardLevel = "Corrosive",
      molarMass = 98.08,
      description = "King of Chemicals. Diprotic, strong dehydrating agent and essential industrial electrolyte."
    ),
    Chemical(
      id = "cuso4",
      name = "Copper(II) Sulfate",
      formula = "CuSO4",
      category = ChemicalCategory.SALT,
      state = "Solution",
      colorHex = 0xDD2563EB,
      ph = 4.5,
      hazardLevel = "Irritant",
      molarMass = 159.61,
      description = "Vibrant deep sapphire blue salt solution. Used for displacement reactions and crystallization."
    ),
    Chemical(
      id = "phenol",
      name = "Phenolphthalein",
      formula = "C20H14O4",
      category = ChemicalCategory.INDICATOR,
      state = "Solution",
      colorHex = 0x66FFFFFF,
      ph = 7.0,
      hazardLevel = "Safe",
      molarMass = 318.32,
      description = "Acid-base indicator. Colorless in acidic solutions, vivid magenta pink in alkaline solutions (pH > 8.2)."
    ),
    Chemical(
      id = "methyl_orange",
      name = "Methyl Orange",
      formula = "C14H14N3NaO3S",
      category = ChemicalCategory.INDICATOR,
      state = "Solution",
      colorHex = 0xFFF97316,
      ph = 4.0,
      hazardLevel = "Safe",
      molarMass = 327.33,
      description = "Turns reddish-pink in acidic medium (pH < 3.1) and bright golden yellow in neutral to basic solutions."
    ),
    Chemical(
      id = "mg",
      name = "Magnesium Ribbon",
      formula = "Mg",
      category = ChemicalCategory.METAL,
      state = "Solid",
      colorHex = 0xFF94A3B8,
      ph = 7.0,
      hazardLevel = "Flammable",
      molarMass = 24.31,
      description = "Lightweight silvery reactive metal ribbon. Burns with an intensely bright blinding white flame."
    ),
    Chemical(
      id = "zn",
      name = "Zinc Granules",
      formula = "Zn",
      category = ChemicalCategory.METAL,
      state = "Solid",
      colorHex = 0xFF64748B,
      ph = 7.0,
      hazardLevel = "Safe",
      molarMass = 65.38,
      description = "Bluish-gray granules. Readily displaces hydrogen gas from dilute mineral acids."
    ),
    Chemical(
      id = "agno3",
      name = "Silver Nitrate",
      formula = "AgNO3",
      category = ChemicalCategory.SALT,
      state = "Solution",
      colorHex = 0xAAEEF2F6,
      ph = 6.0,
      hazardLevel = "Corrosive",
      molarMass = 169.87,
      description = "Precipitates halide anions as distinctive colored silver halides (white AgCl, pale cream AgBr, yellow AgI)."
    ),
    Chemical(
      id = "bacl2",
      name = "Barium Chloride",
      formula = "BaCl2",
      category = ChemicalCategory.SALT,
      state = "Solution",
      colorHex = 0x88F1F5F9,
      ph = 6.5,
      hazardLevel = "Toxic",
      molarMass = 208.23,
      description = "Standard analytical reagent for testing sulfate ions, forming thick insoluble white BaSO4 precipitate."
    ),
    Chemical(
      id = "ki",
      name = "Potassium Iodide",
      formula = "KI",
      category = ChemicalCategory.SALT,
      state = "Solution",
      colorHex = 0x55FFFFFF,
      ph = 7.0,
      hazardLevel = "Safe",
      molarMass = 166.00,
      description = "Colorless iodide salt. Yields golden yellow lead iodide crystals in precipitation reactions."
    ),
    Chemical(
      id = "pbno32",
      name = "Lead(II) Nitrate",
      formula = "Pb(NO3)2",
      category = ChemicalCategory.SALT,
      state = "Solution",
      colorHex = 0x88FFFFFF,
      ph = 4.0,
      hazardLevel = "Toxic",
      molarMass = 331.20,
      description = "Soluble heavy metal nitrate used in the classic 'Golden Rain' precipitation experiment with KI."
    ),
    Chemical(
      id = "caco3",
      name = "Calcium Carbonate",
      formula = "CaCO3",
      category = ChemicalCategory.SALT,
      state = "Solid",
      colorHex = 0xFFF8FAFC,
      ph = 9.0,
      hazardLevel = "Safe",
      molarMass = 100.09,
      description = "Chalk or marble chips. Effervesces rapidly when mixed with acid, releasing bubbling carbon dioxide."
    ),
    Chemical(
      id = "ethanol",
      name = "Ethanol",
      formula = "C2H5OH",
      category = ChemicalCategory.ORGANIC,
      state = "Liquid",
      colorHex = 0x66E0F2FE,
      ph = 7.0,
      hazardLevel = "Flammable",
      molarMass = 46.07,
      description = "Volatile, flammable alcohol. Primary reagent in esterification synthesis with organic acids."
    ),
    Chemical(
      id = "ch3cooh",
      name = "Acetic Acid (Ethanoic)",
      formula = "CH3COOH",
      category = ChemicalCategory.ORGANIC,
      state = "Liquid",
      colorHex = 0x88F0FDF4,
      ph = 2.9,
      hazardLevel = "Corrosive",
      molarMass = 60.05,
      description = "Weak carboxylic acid with sharp vinegar aroma. Reacts with alcohols to produce sweet-smelling esters."
    ),
    Chemical(
      id = "water",
      name = "Distilled Water",
      formula = "H2O",
      category = ChemicalCategory.WATER,
      state = "Liquid",
      colorHex = 0x4438BDF8,
      ph = 7.0,
      hazardLevel = "Safe",
      molarMass = 18.02,
      description = "Universal solvent essential for preparing standard aqueous solutions and diluting concentrations."
    )
  )

  val apparatusList = listOf(
    ApparatusItem("beaker", "Glass Beaker", "Graduated cylindrical vessel with spout for holding, heating, and mixing liquids.", 250, "🥛"),
    ApparatusItem("conical_flask", "Conical (Erlenmeyer) Flask", "Narrow neck prevents splashing during swirling and titration titrant addition.", 250, "🧪"),
    ApparatusItem("test_tube", "Test Tube", "Clear glass tube for observing qualitative color changes, gas bubbles, and precipitates.", 25, "🧪"),
    ApparatusItem("measuring_cylinder", "Measuring Cylinder", "Accurately measures liquid volumes with calibrated graduation marks.", 100, "📏"),
    ApparatusItem("burette", "Burette with Stopcock", "Precision dispenser delivering controlled dropwise titrant volumes for volumetric analysis.", 50, "⚗️"),
    ApparatusItem("pipette", "Graduated Pipette", "Delivers fixed aliquot sample volumes into titration flasks.", 25, "💉"),
    ApparatusItem("dropper", "Dropper Pipette", "Dispenses chemical reagents and indicator solutions drop by drop.", 5, "💧"),
    ApparatusItem("bunsen_burner", "Bunsen Burner & Tripod", "Adjustable gas flame for heating solutions, boiling, and metal flame tests.", 0, "🔥"),
    ApparatusItem("thermometer", "Digital Thermometer", "Monitors exothermic and endothermic reaction temperature shifts in real time.", 150, "🌡️"),
    ApparatusItem("balance", "Electronic Analytical Balance", "Precision mass balance displaying weights to 0.01g precision.", 500, "⚖️"),
    ApparatusItem("ph_meter", "Digital pH Probe", "Direct electrical sensor measuring hydrogen ion activity from 0.0 to 14.0 pH.", 0, "📊")
  )
}
