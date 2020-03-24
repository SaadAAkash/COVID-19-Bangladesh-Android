package ninja.saad.palaocorona.ui.features.liveupdates

import ninja.saad.palaocorona.base.ui.BaseViewModel
import ninja.saad.palaocorona.data.liveupdates.LiveUpdateRepository
import javax.inject.Inject

class LiveUpdatesViewModel @Inject constructor(val repository: LiveUpdateRepository): BaseViewModel() {
}