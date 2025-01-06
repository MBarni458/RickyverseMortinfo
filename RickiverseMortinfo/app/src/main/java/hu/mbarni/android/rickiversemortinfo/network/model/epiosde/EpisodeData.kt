package hu.mbarni.android.rickiversemortinfo.network.model.epiosde

data class EpisodeData(
    val air_date: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)

fun EpisodeData.getSeason() = this.episode.substringAfter("S").substringBefore("E").toInt()
fun EpisodeData.getEpisodePerSeason() = this.episode.substringAfter("E").toInt()