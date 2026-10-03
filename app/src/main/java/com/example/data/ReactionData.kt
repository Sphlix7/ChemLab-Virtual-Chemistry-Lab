package com.example.data

import com.example.model.ChemicalReaction
import com.example.model.ReactionType

object ReactionData {
  val reactions = listOf(
    ChemicalReaction(
      reactants = listOf("hcl", "naoh"),
      products = listOf("nacl", "water"),
      equation = "HCl (aq) + NaOH (aq) → NaCl (aq) + H2O (l)",
      reactionType = ReactionType.NEUTRALIZATION,
      colorChange = "Clear solution; if indicator added: Pink shifts to Colorless",
      tempChange = "+12.4°C Exothermic heat release",
      gasProduced = null,
      precipitateColor = null,
      resultingColorHex = 0x5538BDF8,
      explanation = "Strong acid HCl neutralizes strong base NaOH forming table salt (sodium chloride) and water. Hydrogen ions H+ and hydroxide ions OH- combine to form neutral H2O molecules with substantial enthalpy release (ΔH = -57.3 kJ/mol).",
      safetyWarning = "Exothermic reaction. Concentrated solutions can generate intense localized heat."
    ),
    ChemicalReaction(
      reactants = listOf("pbno32", "ki"),
      products = listOf("pbi2", "kno3"),
      equation = "Pb(NO3)2 (aq) + 2KI (aq) → PbI2 (s)↓ + 2KNO3 (aq)",
      reactionType = ReactionType.PRECIPITATION,
      colorChange = "Instant brilliant golden-yellow precipitate sparkles in solution ('Golden Rain')",
      tempChange = "Minimal temperature change (±0.8°C)",
      gasProduced = null,
      precipitateColor = "Golden Yellow",
      resultingColorHex = 0xFFFACC15,
      explanation = "Double displacement precipitation: Lead cations (Pb²⁺) and iodide anions (I⁻) exceed the solubility product constant (Ksp) and instantly crystallize into brilliant yellow insoluble Lead(II) Iodide.",
      safetyWarning = "Lead compounds are toxic. Virtual lab handles this safely without heavy metal waste!"
    ),
    ChemicalReaction(
      reactants = listOf("cuso4", "zn"),
      products = listOf("znso4", "cu"),
      equation = "CuSO4 (aq) + Zn (s) → ZnSO4 (aq) + Cu (s)↓",
      reactionType = ReactionType.DISPLACEMENT,
      colorChange = "Deep blue color gradually fades to colorless; reddish-brown copper deposits on zinc",
      tempChange = "+7.5°C Moderately exothermic",
      gasProduced = null,
      precipitateColor = "Reddish-Brown copper metal",
      resultingColorHex = 0xAA94A3B8,
      explanation = "Single displacement redox reaction. Zinc is higher in the electrochemical activity series than copper, so zinc atoms oxidize into Zn²⁺ ions while Cu²⁺ ions reduce into elemental reddish-brown copper metal.",
      safetyWarning = "Handle reaction mixture carefully; fine copper precipitate deposits on glassware."
    ),
    ChemicalReaction(
      reactants = listOf("hcl", "caco3"),
      products = listOf("cacl2", "water", "co2"),
      equation = "2HCl (aq) + CaCO3 (s) → CaCl2 (aq) + H2O (l) + CO2 (g)↑",
      reactionType = ReactionType.DOUBLE_DISPLACEMENT,
      colorChange = "Vigorous fizzing effervescence, marble chip dissolves into clear solution",
      tempChange = "+4.2°C Exothermic",
      gasProduced = "Carbon Dioxide (CO2)",
      precipitateColor = null,
      resultingColorHex = 0x4467E8F9,
      explanation = "Acid-carbonate reaction. Hydrochloric acid attacks solid calcium carbonate, liberating carbonic acid which spontaneously decomposes into water and rapidly bubbling carbon dioxide gas (CO2).",
      safetyWarning = "Rapid gas generation can cause pressure buildup if sealed. Keep container vented."
    ),
    ChemicalReaction(
      reactants = listOf("hcl", "mg"),
      products = listOf("mgcl2", "h2"),
      equation = "Mg (s) + 2HCl (aq) → MgCl2 (aq) + H2 (g)↑",
      reactionType = ReactionType.DISPLACEMENT,
      colorChange = "Vigorous bubbling effervescence; magnesium ribbon rapidly dissolves",
      tempChange = "+18.2°C Strongly exothermic",
      gasProduced = "Hydrogen Gas (H2 - 'Pop test')",
      precipitateColor = null,
      resultingColorHex = 0x33A5F3FC,
      explanation = "Active metal displacement. Magnesium ribbon displaces hydrogen from hydrochloric acid. The bubbling gas ignited with a glowing splint makes a characteristic sharp squeaky 'pop' sound.",
      safetyWarning = "Hydrogen gas is flammable! Never expose to open flame near bulk reactions."
    ),
    ChemicalReaction(
      reactants = listOf("bacl2", "h2so4"),
      products = listOf("baso4", "hcl"),
      equation = "BaCl2 (aq) + H2SO4 (aq) → BaSO4 (s)↓ + 2HCl (aq)",
      reactionType = ReactionType.PRECIPITATION,
      colorChange = "Instant dense milky-white precipitate forms",
      tempChange = "+2.1°C Mildly exothermic",
      gasProduced = null,
      precipitateColor = "Chalky White",
      resultingColorHex = 0xFFF1F5F9,
      explanation = "Qualitative test for sulfate ions. Insoluble barium sulfate (BaSO4) has an exceptionally low solubility product, causing an immediate heavy white precipitate that does not dissolve in dilute acid.",
      safetyWarning = "Barium salts are toxic if ingested; precipitation locks ions into safe insoluble BaSO4."
    ),
    ChemicalReaction(
      reactants = listOf("agno3", "hcl"),
      products = listOf("agcl", "hno3"),
      equation = "AgNO3 (aq) + HCl (aq) → AgCl (s)↓ + HNO3 (aq)",
      reactionType = ReactionType.PRECIPITATION,
      colorChange = "Dense curdy white precipitate forms instantly, turns violet in sunlight",
      tempChange = "+1.5°C",
      gasProduced = null,
      precipitateColor = "Curdy White (Silver Chloride)",
      resultingColorHex = 0xFFE2E8F0,
      explanation = "Classic chloride ion confirmation test. Silver ions combine with chloride ions forming insoluble curdy white AgCl precipitate, which is soluble in dilute aqueous ammonia.",
      safetyWarning = "Silver nitrate stains skin and clothing black upon photolytic reduction."
    ),
    ChemicalReaction(
      reactants = listOf("ch3cooh", "ethanol"),
      products = listOf("ch3cooc2h5", "water"),
      equation = "CH3COOH (l) + C2H5OH (l) ⇌ CH3COOC2H5 (l) + H2O (l)",
      reactionType = ReactionType.COMBINATION,
      colorChange = "Solution remains clear; develops a sweet, fruity pear/apple ester fragrance",
      tempChange = "Requires heating (+60°C with catalytic H2SO4)",
      gasProduced = null,
      precipitateColor = null,
      resultingColorHex = 0x66FDE047,
      explanation = "Fischer Esterification: Reversible condensation between ethanoic acid and ethanol in the presence of concentrated sulfuric acid catalyst produces ethyl ethanoate (ethyl acetate), an ester with pleasant fruity aroma.",
      safetyWarning = "Flammable reactants! Heat using a warm water bath rather than a direct open flame."
    )
  )

  fun findReaction(reactants: List<String>): ChemicalReaction? {
    val set = reactants.map { it.lowercase() }.toSet()
    return reactions.firstOrNull { reaction ->
      val reactionSet = reaction.reactants.map { it.lowercase() }.toSet()
      reactionSet == set
    }
  }
}
