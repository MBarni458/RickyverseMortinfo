package hu.mbarni.android.rickiversemortinfo.data.rating.firebase

import com.google.firebase.firestore.FirebaseFirestore
import hu.mbarni.android.rickiversemortinfo.data.rating.RatingService
import hu.mbarni.android.rickiversemortinfo.data.rating.model.Rating
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class FirebaseEpisodeRatingService @Inject constructor(
    private val firestore: FirebaseFirestore
) : RatingService {
    override suspend fun addRating(rating: Rating) {
        firestore.collection(RATING_COLLECTION).document(rating.episodeID.toString()).set(rating)
    }

    override suspend fun getRating(rating: MutableStateFlow<Rating?>) {
        firestore.collection(RATING_COLLECTION).document(rating.value?.episodeID.toString())
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                snapshot?.data?.let { data ->
                    try {
                        val episodeID = data["episodeID"].toString().toInt()
                        val score = data["score"].toString().toInt()
                        val votes = data["votes"].toString().toInt()
                        rating.tryEmit(Rating(episodeID, score, votes))
                    } catch (e: Exception) {
                        rating.tryEmit(null)
                    }
                }
            }
    }

    companion object {
        private const val RATING_COLLECTION = "ratings"
    }
}
