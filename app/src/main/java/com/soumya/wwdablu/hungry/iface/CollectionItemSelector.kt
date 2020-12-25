package com.soumya.wwdablu.hungry.iface

import com.soumya.wwdablu.hungry.model.network.collections.CollectionInfo

interface CollectionItemSelector {
    fun onCollectionClicked(collection: CollectionInfo)
}