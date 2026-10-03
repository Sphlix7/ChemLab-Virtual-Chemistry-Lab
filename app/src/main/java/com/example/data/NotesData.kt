package com.example.data

import com.example.model.ChemistryNote

object NotesData {
  val notes = listOf(
    ChemistryNote(
      id = "note_acids",
      title = "Acids, Bases & pH Scale",
      category = "General Chemistry",
      definition = "Arrhenius defined acids as hydrogen ion (H⁺) donors in water, and bases as hydroxide ion (OH⁻) donors. Brønsted-Lowry expanded this: acids are proton donors, bases are proton acceptors.",
      keyFormulas = listOf(
        "pH = -log₁₀[H⁺]",
        "pOH = -log₁₀[OH⁻]",
        "pH + pOH = 14 (at 25°C)",
        "Kw = [H⁺][OH⁻] = 1.0 × 10⁻¹⁴"
      ),
      equations = listOf(
        "HCl (aq) + H2O (l) → H3O⁺ (aq) + Cl⁻ (aq)",
        "NaOH (aq) → Na⁺ (aq) + OH⁻ (aq)",
        "H⁺ (aq) + OH⁻ (aq) → H2O (l)   [ΔH = -57.3 kJ/mol]"
      ),
      examTips = listOf(
        "Strong acids/bases dissociate 100% in dilute solutions; weak acids require Ka equilibrium expressions.",
        "Remember that a 1-unit decrease in pH represents a 10-fold increase in [H⁺] concentration!",
        "In titrations, the equivalence point is NOT always pH 7: weak acid + strong base yields a basic equivalence point (pH > 7)."
      ),
      simpleExplanation = "Think of pH like a spicy scale for hydrogen ions! 7 is plain water. Anything under 7 is sour like lemon juice (acidic). Anything over 7 is soapy and slippery like baking soda or bleach (basic).",
      examples = listOf(
        "Stomach acid (HCl) has a pH of ~1.5 to kill bacteria and activate digestive pepsin.",
        "Blood pH is tightly buffered by carbonic acid-bicarbonate between 7.35 and 7.45."
      ),
      quizCategory = "Acids & Bases"
    ),

    ChemistryNote(
      id = "note_stoichiometry",
      title = "Stoichiometry & The Mole Concept",
      category = "Physical Chemistry",
      definition = "A mole is the SI unit representing 6.022 × 10²³ elementary entities (Avogadro's constant NA). Stoichiometry uses balanced chemical coefficients to calculate mass, mole, and volume proportions.",
      keyFormulas = listOf(
        "Moles (n) = Mass (m) / Molar Mass (M)",
        "Molarity (M) = Moles of solute / Volume of solution in Liters (V)",
        "Dilution Formula: M₁V₁ = M₂V₂",
        "Molar Gas Volume at STP = 22.4 L/mol (or 24.0 L/mol at RTP)"
      ),
      equations = listOf(
        "2H2 (g) + O2 (g) → 2H2O (l)",
        "N2 (g) + 3H2 (g) ⇌ 2NH3 (g)",
        "n = N / NA = m / M = V / Vm"
      ),
      examTips = listOf(
        "Always convert grams to moles FIRST before using the stoichiometric ratio from the balanced equation.",
        "To find the limiting reactant, divide each reactant's available moles by its stoichiometric coefficient; the smallest value limits the reaction.",
        "Theoretical yield assumes 100% conversion; % Yield = (Actual Yield / Theoretical Yield) × 100."
      ),
      simpleExplanation = "Think of a chemical recipe like baking cookies: 2 cups flour + 1 egg = 1 batch. The mole is just a baker's dozen, but for trillions of tiny atoms so chemists can weigh them on a kitchen scale!",
      examples = listOf(
        "1 mole of pure water (H2O) weighs exactly 18.02 grams and contains 6.022 × 10²³ water molecules.",
        "Burning 16g of methane (CH4, 1 mol) consumes 64g of O2 (2 mol) producing 44g CO2 and 36g H2O."
      ),
      quizCategory = "Stoichiometry"
    ),

    ChemistryNote(
      id = "note_bonding",
      title = "Chemical Bonding & Molecular Shapes",
      category = "Inorganic Chemistry",
      definition = "Chemical bonds are electrostatic attractive forces that hold atoms together to achieve stable octet/duplet valence electron configurations. Includes ionic, covalent (polar/nonpolar), metallic, and intermolecular bonds.",
      keyFormulas = listOf(
        "Formal Charge = Valence e⁻ - Nonbonding e⁻ - (Bonding e⁻ / 2)",
        "Bond Order = (Bonding e⁻ - Antibonding e⁻) / 2",
        "Electronegativity Difference: ΔEN > 1.7 (Ionic), 0.4 - 1.7 (Polar Covalent), < 0.4 (Nonpolar)"
      ),
      equations = listOf(
        "Na(s) + ½Cl2(g) → NaCl(s)   [Ionic lattice formation]",
        "H· + ·H → H:H (H-H)         [Single covalent sigma bond]",
        ":N:::N:                     [Triple covalent bond: 1 σ + 2 π]"
      ),
      examTips = listOf(
        "VSEPR theory states electron pairs repel each other to maximize distance and minimize potential energy.",
        "Lone pairs repel more strongly than bonding pairs, compressing bond angles (e.g., CH4 tetrahedral 109.5°, NH3 pyramidal 107°, H2O bent 104.5°).",
        "Hydrogen bonding only occurs when hydrogen is covalently bonded to highly electronegative N, O, or F."
      ),
      simpleExplanation = "Atoms are happiest when their outer energy levels are full. In ionic bonds, greedy nonmetals steal electrons from generous metals. In covalent bonds, atoms share electrons like friends sharing a textbook.",
      examples = listOf(
        "Table salt (NaCl) forms a rigid crystalline cube where each Na⁺ is surrounded by 6 Cl⁻ ions.",
        "Water's bent shape and polar O-H bonds give it surface tension, high boiling point, and the ability to dissolve salts."
      ),
      quizCategory = "Chemical Bonding"
    ),

    ChemistryNote(
      id = "note_organic",
      title = "Organic Functional Groups & Reactions",
      category = "Organic Chemistry",
      definition = "Functional groups are specific groupings of atoms within molecules that dictate characteristic chemical reactivity regardless of the carbon chain backbone.",
      keyFormulas = listOf(
        "Alkanes: CnH2n+2 (Single C-C bonds, saturated)",
        "Alkenes: CnH2n (Double C=C bond, unsaturated)",
        "Alkynes: CnH2n-2 (Triple C≡C bond)",
        "Alcohols: R-OH | Aldehydes: R-CHO | Ketones: R-CO-R | Carboxylic Acids: R-COOH | Esters: R-COO-R'"
      ),
      equations = listOf(
        "C2H4 + Br2 → CH2Br-CH2Br   [Bromine water unsaturation test: orange decolorizes]",
        "R-CHO + 2[Ag(NH3)2]⁺ + 3OH⁻ → R-COO⁻ + 2Ag↓ + 4NH3 + 2H2O   [Tollens' Silver Mirror]",
        "CH3COOH + C2H5OH ⇌ CH3COOC2H5 + H2O   [Acid-catalyzed esterification]"
      ),
      examTips = listOf(
        "Distinguish aldehydes from ketones using Fehling's or Tollens' reagent; only aldehydes with free formyl hydrogen oxidize to give a positive test.",
        "Primary alcohols oxidize to aldehydes then carboxylic acids; secondary alcohols oxidize to ketones; tertiary alcohols resist oxidation.",
        "Markovnikov's rule: in electrophilic addition to unsymmetrical alkenes, hydrogen attaches to the carbon with more hydrogen atoms."
      ),
      simpleExplanation = "Carbon is like chemical Lego! By snapping on different functional pieces—like an -OH alcohol group or a -COOH acid group—you transform plain wax into sweet fruit flavor, disinfectant, or vinegar.",
      examples = listOf(
        "Isoamyl acetate gives bananas their distinctive smell.",
        "Saponification of vegetable oils with sodium hydroxide produces everyday soap bars and glycerol."
      ),
      quizCategory = "Organic Chemistry"
    ),

    ChemistryNote(
      id = "note_safety",
      title = "Laboratory Safety & Hazardous Materials",
      category = "Lab Safety",
      definition = "Laboratory safety protocols minimize exposure to toxic fumes, chemical burns, explosions, and cryogenic hazards through engineering controls, Personal Protective Equipment (PPE), and proper waste segregation.",
      keyFormulas = listOf(
        "Rule 1: Always Add Acid to Water (A&A - Never Pour Water into Concentrated Acid)",
        "GHS Hazard Pictograms: Flame, Corrosive, Skull & Crossbones, Health Hazard, Environmental",
        "NFPA 704 Diamond: Blue (Health), Red (Flammability), Yellow (Instability), White (Special)"
      ),
      equations = listOf(
        "H2SO4 (conc) + H2O (l) → H3O⁺ + HSO4⁻   [Extremely exothermic dissolution]",
        "NaHCO3 + Acid spill → Salt + H2O + CO2↑   [Safe weak-base neutralization of spills]"
      ),
      examTips = listOf(
        "Remember the mnemonic: 'Do like you oughta, add acid to the water' to dissipate the intense heat of hydration.",
        "Always read the Safety Data Sheet (SDS) before handling unfamiliar reagents.",
        "Never pipette chemicals by mouth; always use a pipette bulb or mechanical filler."
      ),
      simpleExplanation = "Safety in the lab is all about respecting powerful chemicals. Just like wearing a helmet on a bicycle, goggles protect your irreplaceable eyes, and gloves protect your hands so you can do cool science for decades!",
      examples = listOf(
        "Flammable organic solvents must always be warmed in an electrical water bath, never over a naked Bunsen flame.",
        "Acid spills are neutralized with mild sodium bicarbonate (baking soda), never concentrated lye."
      ),
      quizCategory = "Lab Safety"
    )
  )
}
