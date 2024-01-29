package com.creative.qrcodescanner.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by dan on 08/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

val spaceDp = SpaceDp(
    spaceNone = 0.dp,
    spaceXXSmall = 2.dp,
    spaceXSmall = 4.dp,
    spaceSmall = 8.dp,
    spaceMedium = 16.dp,
    spaceLarge = 24.dp,
    spaceXLarge = 32.dp,
    spaceXXLarge = 40.dp,
    spaceXXXLarge = 48.dp,
)

val borderRadius = BorderRadiusDp(
    borderRadiusNone = 0.dp,
    borderRadiusSmall = 2.dp,
    borderRadiusMedium = 4.dp,
    borderRadiusLarge = 8.dp,
    borderRadiusXLarge = 16.dp,
    borderRadiusXXLarge = 24.dp,
    borderRadiusXXXLarge = 32.dp,
)

val elevation = ElevationDp(
    elevationNone = 0.dp,
    elevationSmall = 2.dp,
    elevationMedium = 4.dp,
    elevationLarge = 8.dp,
    elevationXLarge = 16.dp,
    elevationXXLarge = 24.dp,
    elevationXXXLarge = 32.dp,
)

val shapeSize = ShapeSizeDp(
    shapeSizeNone = 0.dp,
    shapeSizeSmall = 2.dp,
    shapeSizeMedium = 4.dp,
    shapeSizeLarge = 8.dp,
    shapeSizeXLarge = 16.dp,
    shapeSizeXXLarge = 24.dp,
    shapeSizeXXXLarge = 32.dp,
)

data class SpaceDp(
    val spaceNone: Dp,
    val spaceXXSmall: Dp,
    val spaceXSmall: Dp,
    val spaceSmall: Dp,
    val spaceMedium: Dp,
    val spaceLarge: Dp,
    val spaceXLarge: Dp,
    val spaceXXLarge: Dp,
    val spaceXXXLarge: Dp,
)

data class FontSizeSp(
    val heading1: TextUnit,
    val heading2: TextUnit,
    val heading3: TextUnit,
    val heading4: TextUnit,
    val heading5: TextUnit,
    val heading6: TextUnit,
    val heading7: TextUnit,
    val heading8: TextUnit
)

data class BorderRadiusDp(
    val borderRadiusNone: Dp,
    val borderRadiusSmall: Dp,
    val borderRadiusMedium: Dp,
    val borderRadiusLarge: Dp,
    val borderRadiusXLarge: Dp,
    val borderRadiusXXLarge: Dp,
    val borderRadiusXXXLarge: Dp,
)

data class ElevationDp(
    val elevationNone: Dp,
    val elevationSmall: Dp,
    val elevationMedium: Dp,
    val elevationLarge: Dp,
    val elevationXLarge: Dp,
    val elevationXXLarge: Dp,
    val elevationXXXLarge: Dp,
)

data class ShapeSizeDp(
    val shapeSizeNone: Dp,
    val shapeSizeSmall: Dp,
    val shapeSizeMedium: Dp,
    val shapeSizeLarge: Dp,
    val shapeSizeXLarge: Dp,
    val shapeSizeXXLarge: Dp,
    val shapeSizeXXXLarge: Dp,
)