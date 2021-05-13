/*
 * The MIT License
 *
 * Copyright (c) 2019- Nordic Institute for Interoperability Solutions (NIIS)
 * Copyright (c) 2018 Estonian Information System Authority (RIA),
 * Nordic Institute for Interoperability Solutions (NIIS), Population Register Centre (VRK)
 * Copyright (c) 2015-2017 Estonian Information System Authority (RIA), Population Register Centre (VRK)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
// This file is the entry point for the library build
import { VueConstructor } from 'vue';
import ConfirmDialog from './components/ConfirmDialog.vue';
import Expandable from './components/Expandable.vue';
import FileUpload from './components/FileUpload.vue';
import FormLabel from './components/FormLabel.vue';
import HelpDialog from './components/HelpDialog.vue';
import HelpIcon from './components/HelpIcon.vue';
import Button from './components/Button.vue';
import SimpleDialog from './components/SimpleDialog.vue';
import StatusIcon from './components/StatusIcon.vue';
import SubViewTitle from './components/SubViewTitle.vue';
import XrdSearch from './components/XrdSearch.vue';
// Import vee-validate so it's configured on the library build
import './plugins/vee-validate';
import './i18n';
import './assets/icons.css';

const SharedComponents = {
  install(Vue: VueConstructor): void {
    Vue.component('XrdConfirmDialog', ConfirmDialog);
    Vue.component('XrdExpandable', Expandable);
    Vue.component('XrdFileUpload', FileUpload);
    Vue.component('XrdFormLabel', FormLabel);
    Vue.component('XrdHelpDialog', HelpDialog);
    Vue.component('XrdHelpIcon', HelpIcon);
    Vue.component('XrdButton', Button);
    Vue.component('XrdSimpleDialog', SimpleDialog);
    Vue.component('XrdStatusIcon', StatusIcon);
    Vue.component('XrdSubViewTitle', SubViewTitle);
    Vue.component('XrdSearch', XrdSearch);
  },
};

export default SharedComponents;
