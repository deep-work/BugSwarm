# ===============================================================================
# Copyright 2015 Jake Ross
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
from traitsui.api import View, EnumEditor
# ============= standard library imports ========================
# ============= local library imports  ==========================
from pychron.options.options import SubOptions, AppearanceSubOptions, MainOptions, object_column, checkbox_column
from pychron.pychron_constants import FIT_TYPES_INTERPOLATE, FIT_ERROR_TYPES


class BlanksSubOptions(SubOptions):
    def traits_view(self):
        v = View()
        return v


class BlanksAppearance(AppearanceSubOptions):
    pass
    # def traits_view(self):
    #     fgrp = VGroup(UItem('fontname'),
    #                   self._get_xfont_group(),
    #                   self._get_yfont_group(),
    #                   label='Fonts', show_border=True)
    #
    #     v = View(VGroup(self._get_bg_group(),
    #                     self._get_padding_group(),
    #                     fgrp))
    #     return v


class BlanksMainOptions(MainOptions):
    def _get_columns(self):
        return [object_column(name='name'),
                checkbox_column(name='plot_enabled', label='Enabled'),
                checkbox_column(name='save_enabled', label='Save'),
                object_column(name='fit',
                              editor=EnumEditor(values=FIT_TYPES_INTERPOLATE),
                              width=75),
                object_column(name='error_type',
                              editor=EnumEditor(values=FIT_ERROR_TYPES),
                              width=75, label='Error'),
                checkbox_column(name='filter_outliers', label='Out.'),
                object_column(name='filter_outlier_iterations', label='Iter.'),
                object_column(name='filter_outlier_std_devs', label='SD')]

# ===============================================================
# ===============================================================
VIEWS = {}
VIEWS['main'] = BlanksMainOptions
VIEWS['blanks'] = BlanksSubOptions
VIEWS['appearance'] = BlanksAppearance


# ===============================================================
# ===============================================================


# ============= EOF =============================================
