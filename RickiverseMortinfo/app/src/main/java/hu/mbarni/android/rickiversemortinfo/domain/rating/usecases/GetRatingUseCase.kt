package hu.mbarni.android.rickiversemortinfo.domain.rating.usecases

import hu.mbarni.android.rickiversemortinfo.data.rating.RatingService
import hu.mbarni.android.rickiversemortinfo.data.rating.model.Rating
import kotlinx.coroutines.flow.MutableStateFlow

class GetRatingUseCase(private val service: RatingService) {
    suspend operator fun invoke(rating: MutableStateFlow<Rating?>) = service.getRating(rating)
}
