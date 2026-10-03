package com.example.util

import kotlin.math.log10
import kotlin.math.roundToInt

data class CalculationResult(
  val title: String,
  val resultFormatted: String,
  val formulaUsed: String,
  val steps: List<String>,
  val explanation: String
)

object ChemistryCalculatorHelper {

  private val atomicWeights = mapOf(
    "H" to 1.008, "He" to 4.003, "Li" to 6.941, "Be" to 9.012, "B" to 10.811,
    "C" to 12.011, "N" to 14.007, "O" to 15.999, "F" to 18.998, "Ne" to 20.180,
    "Na" to 22.990, "Mg" to 24.305, "Al" to 26.982, "Si" to 28.085, "P" to 30.974,
    "S" to 32.065, "Cl" to 35.453, "K" to 39.098, "Ca" to 40.078, "Fe" to 55.845,
    "Cu" to 63.546, "Zn" to 65.380, "Br" to 79.904, "Ag" to 107.868, "I" to 126.904,
    "Ba" to 137.327, "Pb" to 207.200
  )

  /**
   * Parses chemical formulas including parentheses e.g. H2O, Ca(OH)2, Fe2(SO4)3, C6H12O6
   */
  fun calculateMolarMass(rawFormula: String): CalculationResult {
    val formula = rawFormula.trim()
    if (formula.isEmpty()) {
      return CalculationResult("Molar Mass", "0.00 g/mol", "M = Σ (n × Atomic Weight)", listOf("Enter a chemical formula like H2O, H2SO4, or Ca(OH)2"), "")
    }

    try {
      val elementCounts = parseFormula(formula)
      val steps = mutableListOf<String>()
      var totalMass = 0.0

      for ((elem, count) in elementCounts) {
        val weight = atomicWeights[elem] ?: 0.0
        val subtotal = count * weight
        totalMass += subtotal
        steps.add("$elem: $count atoms × ${"%.3f".format(weight)} g/mol = ${"%.3f".format(subtotal)} g/mol")
      }

      val formattedTotal = "${"%.3f".format(totalMass)} g/mol"
      steps.add("Total Molar Mass = Σ parts = $formattedTotal")

      return CalculationResult(
        title = "Molar Mass for $formula",
        resultFormatted = formattedTotal,
        formulaUsed = "M = Σ (atomic weight × count)",
        steps = steps,
        explanation = "The molar mass is the mass of 1 mole (6.022 × 10²³ particles) of $formula."
      )
    } catch (e: Exception) {
      return CalculationResult(
        title = "Formula Parse Error",
        resultFormatted = "Invalid Formula",
        formulaUsed = "Enter valid symbols like NaCl, H2SO4, C6H12O6",
        steps = listOf("Could not parse elements in: '$formula'. Make sure element symbols use capitalized letters (e.g., Na, Cl, Fe)."),
        explanation = "Ensure correct chemical capitalization (e.g. Ca for Calcium, not ca)."
      )
    }
  }

  fun calculateMolesAndMass(massGrams: Double, molarMass: Double): CalculationResult {
    if (molarMass <= 0) return CalculationResult("Moles", "Error", "n = m / M", listOf("Molar mass must be greater than zero"), "")
    val moles = massGrams / molarMass
    return CalculationResult(
      title = "Moles from Mass",
      resultFormatted = "${"%.4f".format(moles)} moles",
      formulaUsed = "Moles (n) = Mass (m) / Molar Mass (M)",
      steps = listOf(
        "Given mass (m) = $massGrams g",
        "Given molar mass (M) = $molarMass g/mol",
        "n = $massGrams / $molarMass",
        "Calculated moles = ${"%.4f".format(moles)} mol"
      ),
      explanation = "This represents ${(moles * 6.022e23).toScientificString()} molecules/units."
    )
  }

  fun calculateMolarity(moles: Double, volumeLiters: Double): CalculationResult {
    if (volumeLiters <= 0) return CalculationResult("Molarity", "Error", "M = n / V", listOf("Volume must be greater than 0 L"), "")
    val molarity = moles / volumeLiters
    return CalculationResult(
      title = "Molarity (M)",
      resultFormatted = "${"%.4f".format(molarity)} M (mol/L)",
      formulaUsed = "Molarity (M) = Moles of solute (n) / Volume of solution (V in Liters)",
      steps = listOf(
        "Moles of solute (n) = $moles mol",
        "Volume of solution (V) = $volumeLiters L",
        "M = $moles / $volumeLiters",
        "Resulting Molarity = ${"%.4f".format(molarity)} mol/L"
      ),
      explanation = "A 1.0 M solution contains 1 mole of solute dissolved in total volume of 1 liter."
    )
  }

  fun calculateNormality(molarity: Double, valencyOrNFactor: Double): CalculationResult {
    val normality = molarity * valencyOrNFactor
    return CalculationResult(
      title = "Normality (N)",
      resultFormatted = "${"%.4f".format(normality)} N (eq/L)",
      formulaUsed = "Normality (N) = Molarity (M) × Valency factor (n-factor)",
      steps = listOf(
        "Molarity (M) = $molarity mol/L",
        "Valency / n-factor (H⁺ or OH⁻ or valence transfer) = $valencyOrNFactor",
        "N = $molarity × $valencyOrNFactor",
        "Resulting Normality = ${"%.4f".format(normality)} equivalents/L"
      ),
      explanation = "For monoprotic acid (HCl) n=1, so N=M. For diprotic (H2SO4) n=2, so N = 2 × M."
    )
  }

  fun calculateDilution(m1: Double, v1: Double, m2: Double?, v2: Double?): CalculationResult {
    // Solves M1 * V1 = M2 * V2
    if (m2 == null && v2 != null && v2 > 0) {
      val calculatedM2 = (m1 * v1) / v2
      return CalculationResult(
        title = "Dilution Concentration (M₂)",
        resultFormatted = "${"%.4f".format(calculatedM2)} M",
        formulaUsed = "M₁ × V₁ = M₂ × V₂  ⟹  M₂ = (M₁ × V₁) / V₂",
        steps = listOf(
          "Initial concentration (M₁) = $m1 M",
          "Initial volume (V₁) = $v1 mL",
          "Final diluted volume (V₂) = $v2 mL",
          "M₂ = ($m1 × $v1) / $v2 = ${"%.4f".format(calculatedM2)} M"
        ),
        explanation = "The total number of solute moles remains identical before and after adding solvent."
      )
    } else if (v2 == null && m2 != null && m2 > 0) {
      val calculatedV2 = (m1 * v1) / m2
      return CalculationResult(
        title = "Required Final Volume (V₂)",
        resultFormatted = "${"%.2f".format(calculatedV2)} mL",
        formulaUsed = "M₁ × V₁ = M₂ × V₂  ⟹  V₂ = (M₁ × V₁) / M₂",
        steps = listOf(
          "Initial concentration (M₁) = $m1 M",
          "Initial volume (V₁) = $v1 mL",
          "Target concentration (M₂) = $m2 M",
          "V₂ = ($m1 × $v1) / $m2 = ${"%.2f".format(calculatedV2)} mL",
          "Add ${"%.2f".format(calculatedV2 - v1)} mL of distilled water to reach target volume."
        ),
        explanation = "Volume of solvent added = V₂ - V₁."
      )
    }
    return CalculationResult("Dilution", "Enter variables", "M₁V₁ = M₂V₂", listOf("Provide M1, V1, and either M2 or V2"), "")
  }

  fun calculatePhAndPoh(hPlusConcentration: Double): CalculationResult {
    if (hPlusConcentration <= 0) return CalculationResult("pH Calculation", "Error", "pH = -log₁₀[H⁺]", listOf("Concentration must be > 0"), "")
    val ph = -log10(hPlusConcentration)
    val poh = 14.0 - ph
    val type = when {
      ph < 6.8 -> "Acidic 🍋"
      ph > 7.2 -> "Alkaline / Basic 🧼"
      else -> "Neutral 💧"
    }

    return CalculationResult(
      title = "pH & pOH Analysis",
      resultFormatted = "pH: ${"%.2f".format(ph)} | pOH: ${"%.2f".format(poh)} ($type)",
      formulaUsed = "pH = -log₁₀[H⁺]  and  pH + pOH = 14",
      steps = listOf(
        "Hydrogen ion concentration [H⁺] = $hPlusConcentration M",
        "pH = -log₁₀($hPlusConcentration) = ${"%.2f".format(ph)}",
        "pOH = 14 - pH = 14 - ${"%.2f".format(ph)} = ${"%.2f".format(poh)}",
        "Hydroxide ion concentration [OH⁻] = 10^(-pOH) = ${(Math.pow(10.0, -poh)).toScientificString()} M"
      ),
      explanation = "Solutions with pH < 7 have higher [H⁺] than [OH⁻], while pH > 7 has excess [OH⁻]."
    )
  }

  fun calculateGasLaw(pressureAtm: Double, volumeLiters: Double, tempKelvin: Double): CalculationResult {
    // PV = nRT  => n = PV / RT
    val R = 0.082057 // L·atm / (mol·K)
    if (tempKelvin <= 0) return CalculationResult("Gas Laws", "Error", "PV = nRT", listOf("Temperature must be > 0 K (absolute zero)"), "")
    val moles = (pressureAtm * volumeLiters) / (R * tempKelvin)
    return CalculationResult(
      title = "Ideal Gas Law (PV = nRT)",
      resultFormatted = "${"%.4f".format(moles)} moles of gas",
      formulaUsed = "n = (P × V) / (R × T)",
      steps = listOf(
        "Pressure (P) = $pressureAtm atm",
        "Volume (V) = $volumeLiters L",
        "Temperature (T) = $tempKelvin K (${"%.1f".format(tempKelvin - 273.15)}°C)",
        "Gas constant (R) = 0.08206 L·atm/(mol·K)",
        "n = ($pressureAtm × $volumeLiters) / (0.08206 × $tempKelvin) = ${"%.4f".format(moles)} mol"
      ),
      explanation = "Describes behavior of ideal gas particles under non-condensing temperature and pressure."
    )
  }

  fun calculateDensity(massGrams: Double, volumeCm3: Double): CalculationResult {
    if (volumeCm3 <= 0) return CalculationResult("Density", "Error", "d = m / V", listOf("Volume must be > 0"), "")
    val density = massGrams / volumeCm3
    return CalculationResult(
      title = "Density Calculation",
      resultFormatted = "${"%.3f".format(density)} g/cm³",
      formulaUsed = "Density (ρ) = Mass (m) / Volume (V)",
      steps = listOf(
        "Mass (m) = $massGrams g",
        "Volume (V) = $volumeCm3 cm³ (or mL)",
        "ρ = $massGrams / $volumeCm3 = ${"%.3f".format(density)} g/cm³",
        "In SI units: ${"%.1f".format(density * 1000)} kg/m³"
      ),
      explanation = "Pure water has a density of 1.00 g/cm³ at 4°C. Substances with ρ < 1 float on water."
    )
  }

  private fun parseFormula(formula: String): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    var i = 0

    fun parseSub(): Map<String, Int> {
      val subMap = mutableMapOf<String, Int>()
      while (i < formula.length) {
        val c = formula[i]
        if (c == '(') {
          i++
          val inner = parseSub()
          var mult = 0
          while (i < formula.length && formula[i].isDigit()) {
            mult = mult * 10 + (formula[i] - '0')
            i++
          }
          if (mult == 0) mult = 1
          for ((elem, count) in inner) {
            subMap[elem] = (subMap[elem] ?: 0) + (count * mult)
          }
        } else if (c == ')') {
          i++
          return subMap
        } else if (c.isUpperCase()) {
          val elemBuilder = StringBuilder()
          elemBuilder.append(c)
          i++
          while (i < formula.length && formula[i].isLowerCase()) {
            elemBuilder.append(formula[i])
            i++
          }
          val elem = elemBuilder.toString()
          var count = 0
          while (i < formula.length && formula[i].isDigit()) {
            count = count * 10 + (formula[i] - '0')
            i++
          }
          if (count == 0) count = 1
          subMap[elem] = (subMap[elem] ?: 0) + count
        } else {
          i++
        }
      }
      return subMap
    }

    return parseSub()
  }

  private fun Double.toScientificString(): String {
    return String.format("%.2e", this)
  }
}
