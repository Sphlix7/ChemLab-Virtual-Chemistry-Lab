package com.example.data

import com.example.model.ChemicalElement
import com.example.model.ElementCategory

object PeriodicTableData {
  val elements = listOf(
    ChemicalElement(
      1, "H", "Hydrogen", 1.008, "1s¹", 1, 1, 1, "s",
      ElementCategory.NONMETAL, 14.01, 20.28, 0.00008988,
      "Rocket fuel, ammonia synthesis (Haber process), fuel cells, petroleum hydrocracking",
      "H2O, HCl, NH3, CH4", listOf(1)
    ),
    ChemicalElement(
      2, "He", "Helium", 4.0026, "1s²", 0, 18, 1, "s",
      ElementCategory.NOBLE_GAS, 0.95, 4.22, 0.0001785,
      "Cryogenic coolant for MRI magnets, party balloons, deep-sea diving gas mixtures, leak detection",
      "Inert; no stable neutral compounds under standard conditions", listOf(2)
    ),
    ChemicalElement(
      3, "Li", "Lithium", 6.94, "[He] 2s¹", 1, 1, 2, "s",
      ElementCategory.ALKALI_METAL, 453.69, 1603.0, 0.534,
      "Lithium-ion batteries in smartphones & electric vehicles, mood stabilizer medications, lubricating greases",
      "LiOH, Li2CO3, LiCl", listOf(2, 1)
    ),
    ChemicalElement(
      4, "Be", "Beryllium", 9.0122, "[He] 2s²", 2, 2, 2, "s",
      ElementCategory.ALKALINE_EARTH, 1560.0, 2742.0, 1.85,
      "Aerospace alloys, James Webb Space Telescope mirrors, X-ray tube windows",
      "BeO, BeCl2", listOf(2, 2)
    ),
    ChemicalElement(
      5, "B", "Boron", 10.81, "[He] 2s² 2p¹", 3, 13, 2, "p",
      ElementCategory.METALLOID, 2349.0, 4200.0, 2.34,
      "Pyrex borosilicate glassware, fiberglass insulation, semiconductor doping, laundry borax",
      "H3BO3, Na2B4O7·10H2O, BF3", listOf(2, 3)
    ),
    ChemicalElement(
      6, "C", "Carbon", 12.011, "[He] 2s² 2p²", 4, 14, 2, "p",
      ElementCategory.NONMETAL, 3800.0, 4300.0, 2.267,
      "Basis of all organic life, diamond jewelry & cutting tools, graphite electrodes, carbon nanotubes",
      "CO2, CH4, C6H12O6, CaCO3", listOf(2, 4)
    ),
    ChemicalElement(
      7, "N", "Nitrogen", 14.007, "[He] 2s² 2p³", 3, 15, 2, "p",
      ElementCategory.NONMETAL, 63.15, 77.36, 0.0012506,
      "78% of Earth's atmosphere, agricultural fertilizers, cryopreservation in liquid nitrogen, food packaging inert atmosphere",
      "NH3, HNO3, N2O, NO2", listOf(2, 5)
    ),
    ChemicalElement(
      8, "O", "Oxygen", 15.999, "[He] 2s² 2p⁴", 2, 16, 2, "p",
      ElementCategory.NONMETAL, 54.36, 90.20, 0.001429,
      "Cellular respiration, steelmaking combustion, rocket oxidizer, medical oxygen therapy",
      "H2O, CO2, O3, Fe2O3", listOf(2, 6)
    ),
    ChemicalElement(
      9, "F", "Fluorine", 18.998, "[He] 2s² 2p⁵", 1, 17, 2, "p",
      ElementCategory.HALOGEN, 53.53, 85.03, 0.001696,
      "Toothpaste enamel protection (fluoride), Teflon non-stick cookware (PTFE), refrigerants",
      "HF, NaF, CF4, SF6", listOf(2, 7)
    ),
    ChemicalElement(
      10, "Ne", "Neon", 20.180, "[He] 2s² 2p⁶", 0, 18, 2, "p",
      ElementCategory.NOBLE_GAS, 24.56, 27.07, 0.0008999,
      "Red-orange glowing neon advertising signs, high-voltage indicators, cryogenic refrigeration",
      "Inert; noble gas", listOf(2, 8)
    ),
    ChemicalElement(
      11, "Na", "Sodium", 22.990, "[Ne] 3s¹", 1, 1, 3, "s",
      ElementCategory.ALKALI_METAL, 370.87, 1156.0, 0.971,
      "Table salt, sodium-vapor street lamps, soap manufacture (NaOH), biological nerve impulses",
      "NaCl, NaOH, NaHCO3, Na2CO3", listOf(2, 8, 1)
    ),
    ChemicalElement(
      12, "Mg", "Magnesium", 24.305, "[Ne] 3s²", 2, 2, 3, "s",
      ElementCategory.ALKALINE_EARTH, 923.0, 1363.0, 1.738,
      "Chlorophyll in plant photosynthesis, fireworks white flare, lightweight automotive alloys, Epsom salts",
      "MgO, MgSO4, MgCl2, Mg(OH)2", listOf(2, 8, 2)
    ),
    ChemicalElement(
      13, "Al", "Aluminum", 26.982, "[Ne] 3s² 3p¹", 3, 13, 3, "p",
      ElementCategory.POST_TRANSITION, 933.47, 2792.0, 2.70,
      "Aircraft construction, beverage cans, window frames, electrical transmission lines, kitchen foil",
      "Al2O3, AlCl3, Al(OH)3", listOf(2, 8, 3)
    ),
    ChemicalElement(
      14, "Si", "Silicon", 28.085, "[Ne] 3s² 3p²", 4, 14, 3, "p",
      ElementCategory.METALLOID, 1687.0, 3538.0, 2.329,
      "Computer microchips and processors, photovoltaic solar panels, silicone sealants, glass (SiO2)",
      "SiO2, SiC, SiH4", listOf(2, 8, 4)
    ),
    ChemicalElement(
      15, "P", "Phosphorus", 30.974, "[Ne] 3s² 3p³", 3, 15, 3, "p",
      ElementCategory.NONMETAL, 317.3, 553.6, 1.823,
      "DNA/RNA backbone & ATP cellular energy, safety match strike surfaces, agricultural fertilizers",
      "H3PO4, P2O5, PCl5", listOf(2, 8, 5)
    ),
    ChemicalElement(
      16, "S", "Sulfur", 32.06, "[Ne] 3s² 3p⁴", 2, 16, 3, "p",
      ElementCategory.NONMETAL, 388.36, 717.8, 2.07,
      "Sulfuric acid production, vulcanization of rubber tires, gunpowder, fungicides, matches",
      "H2SO4, SO2, FeS2, H2S", listOf(2, 8, 6)
    ),
    ChemicalElement(
      17, "Cl", "Chlorine", 35.45, "[Ne] 3s² 3p⁵", 1, 17, 3, "p",
      ElementCategory.HALOGEN, 171.6, 239.11, 0.003214,
      "Drinking water purification, swimming pool sanitation, PVC pipe manufacturing, household bleach",
      "HCl, NaCl, ClO2, CCl4", listOf(2, 8, 7)
    ),
    ChemicalElement(
      18, "Ar", "Argon", 39.948, "[Ne] 3s² 3p⁶", 0, 18, 3, "p",
      ElementCategory.NOBLE_GAS, 83.80, 87.30, 0.001784,
      "Inert shielding gas for TIG welding, incandescent light bulbs, double-pane window thermal insulation",
      "Inert; noble gas", listOf(2, 8, 8)
    ),
    ChemicalElement(
      19, "K", "Potassium", 39.098, "[Ar] 4s¹", 1, 1, 4, "s",
      ElementCategory.ALKALI_METAL, 336.53, 1032.0, 0.862,
      "NPK agricultural fertilizer (potash), essential cellular electrolyte, potassium hydroxide soap, fireworks lilac flames",
      "KCl, KOH, KNO3, KI", listOf(2, 8, 8, 1)
    ),
    ChemicalElement(
      20, "Ca", "Calcium", 40.078, "[Ar] 4s²", 2, 2, 4, "s",
      ElementCategory.ALKALINE_EARTH, 1115.0, 1757.0, 1.54,
      "Human bone and teeth structure, Portland cement, limestone construction, plaster of Paris",
      "CaCO3, CaO, Ca(OH)2, CaCl2", listOf(2, 8, 8, 2)
    ),
    ChemicalElement(
      26, "Fe", "Iron", 55.845, "[Ar] 3d⁶ 4s²", 2, 8, 4, "d",
      ElementCategory.TRANSITION_METAL, 1811.0, 3134.0, 7.874,
      "Steel structures, hemoglobin oxygen transport in blood, electromagnets, industrial tools",
      "Fe2O3, FeCl3, FeSO4", listOf(2, 8, 14, 2)
    ),
    ChemicalElement(
      29, "Cu", "Copper", 63.546, "[Ar] 3d¹⁰ 4s¹", 2, 11, 4, "d",
      ElementCategory.TRANSITION_METAL, 1357.77, 2835.0, 8.96,
      "Electrical wiring & printed circuit boards, plumbing pipes, bronze/brass alloys, antimicrobial surfaces",
      "CuSO4, CuO, CuCl2", listOf(2, 8, 18, 1)
    ),
    ChemicalElement(
      30, "Zn", "Zinc", 65.38, "[Ar] 3d¹⁰ 4s²", 2, 12, 4, "d",
      ElementCategory.TRANSITION_METAL, 692.68, 1180.0, 7.14,
      "Galvanizing steel against rust, die-casting, brass alloy component, dietary immune enzyme support",
      "ZnO, ZnSO4, ZnCl2", listOf(2, 8, 18, 2)
    ),
    ChemicalElement(
      35, "Br", "Bromine", 79.904, "[Ar] 3d¹⁰ 4s² 4p⁵", 1, 17, 4, "p",
      ElementCategory.HALOGEN, 265.8, 332.0, 3.1028,
      "Flame retardants, pharmaceutical synthesis, photography silver bromide emulsion",
      "HBr, KBr, AgBr", listOf(2, 8, 18, 7)
    ),
    ChemicalElement(
      47, "Ag", "Silver", 107.87, "[Kr] 4d¹⁰ 5s¹", 1, 11, 5, "d",
      ElementCategory.TRANSITION_METAL, 1234.93, 2435.0, 10.49,
      "Highest electrical & thermal conductivity of any metal, jewelry, mirrors, solar cell contacts",
      "AgNO3, AgCl, Ag2O", listOf(2, 8, 18, 18, 1)
    ),
    ChemicalElement(
      79, "Au", "Gold", 196.97, "[Xe] 4f¹⁴ 5d¹⁰ 6s¹", 1, 11, 6, "d",
      ElementCategory.TRANSITION_METAL, 1337.33, 3129.0, 19.30,
      "Jewelry, monetary reserves, corrosion-free electrical contacts, space radiation shielding",
      "AuCl3, HAuCl4", listOf(2, 8, 18, 32, 18, 1)
    ),
    ChemicalElement(
      80, "Hg", "Mercury", 200.59, "[Xe] 4f¹⁴ 5d¹⁰ 6s²", 2, 12, 6, "d",
      ElementCategory.TRANSITION_METAL, 234.32, 629.88, 13.534,
      "Only metal liquid at room temperature; historic barometers, fluorescent lamps, dental amalgams",
      "HgO, HgCl2, HgS", listOf(2, 8, 18, 32, 18, 2)
    ),
    ChemicalElement(
      82, "Pb", "Lead", 207.2, "[Xe] 4f¹⁴ 5d¹⁰ 6s² 6p²", 2, 14, 6, "p",
      ElementCategory.POST_TRANSITION, 600.61, 2022.0, 11.34,
      "Automobile lead-acid batteries, radiation shielding aprons, underwater cable sheathing",
      "Pb(NO3)2, PbI2, PbO2", listOf(2, 8, 18, 32, 18, 4)
    )
  )
}
