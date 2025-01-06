package hu.mbarni.android.rickiversemortinfo.data.rating

import hu.mbarni.android.rickiversemortinfo.data.rating.model.Rating
import kotlinx.coroutines.flow.MutableStateFlow

interface RatingService {
    suspend fun addRating(rating: Rating)
    suspend fun getRating(rating: MutableStateFlow<Rating?>)
}