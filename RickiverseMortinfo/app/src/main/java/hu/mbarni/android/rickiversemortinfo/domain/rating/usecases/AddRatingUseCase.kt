package hu.mbarni.android.rickiversemortinfo.domain.rating.usecases

import hu.mbarni.android.rickiversemortinfo.data.rating.RatingService
import hu.mbarni.android.rickiversemortinfo.data.rating.model.Rating

class AddRatingUseCase(private val service: RatingService) {
    suspend operator fun invoke(rating: Rating) = service.addRating(rating)
}
