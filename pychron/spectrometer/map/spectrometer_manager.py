# ===============================================================================
# Copyright 2014 Jake Ross
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# ===============================================================================

# ============= enthought library imports =======================
# ============= standard library imports ========================
# ============= local library imports  ==========================
from pychron.spectrometer.base_spectrometer_manager import BaseSpectrometerManager
from pychron.spectrometer.map.spectrometer import MapSpectrometer


class MapSpectrometerManager(BaseSpectrometerManager):
    spectrometer_klass = MapSpectrometer
    def load(self, *args, **kw):
        self.spectrometer.load()

    def finish_loading(self):
        self.spectrometer.finish_loading()

# ============= EOF =============================================



