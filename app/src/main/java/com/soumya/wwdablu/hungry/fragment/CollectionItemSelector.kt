package com.soumya.wwdablu.hungry.fragment

import com.soumya.wwdablu.hungry.model.network.collections.CollectionInfo

interface CollectionItemSelector {
    fun onCollectionClicked(collection: CollectionInfo)
}