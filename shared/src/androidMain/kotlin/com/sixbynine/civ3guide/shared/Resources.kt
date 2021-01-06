package com.sixbynine.civ3guide.shared

import dev.icerock.moko.resources.desc.StringDesc

actual fun StringDesc.load() = this.toString(Civ3GuideApplication.instance)