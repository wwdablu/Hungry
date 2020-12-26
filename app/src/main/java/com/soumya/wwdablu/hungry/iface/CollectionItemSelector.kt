package com.soumya.wwdablu.hungry.iface

import com.soumya.wwdablu.hungry.network.model.collections.CollectionInfo

interface CollectionItemSelector {
    fun onCollectionClicked(collection: CollectionInfo)
}