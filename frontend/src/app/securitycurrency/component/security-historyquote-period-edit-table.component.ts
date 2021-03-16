import {ChangeDetectorRef, Component} from '@angular/core';
import {MessageToastService} from '../../shared/message/message.toast.service';
import {TranslateService} from '@ngx-translate/core';
import {GlobalparameterService} from '../../shared/service/globalparameter.service';
import {UserSettingsService} from '../../shared/service/user.settings.service';
import {DataType} from '../../dynamic-form/models/data.type';
import {SplitPeriodTableBase} from './split.period.table.base';
import {HistoryquotePeriod} from '../../entities/historyquote.period';
import {HistoryquotePeriodService} from '../service/historyquote.period.service';
import {FilterService} from 'primeng/api';


@Component({
  selector: 'security-historyquote-period-edit-table',
  templateUrl: '../view/split.period.table.html'
})
export class SecurityHistoryquotePeriodEditTableComponent extends SplitPeriodTableBase<HistoryquotePeriod> {
  readonly dataSortKey = 'fromDate';

  constructor(historyquotePeriodService: HistoryquotePeriodService,
              messageToastService: MessageToastService,
              changeDetectionStrategy: ChangeDetectorRef,
              filterService: FilterService,
              usersettingsService: UserSettingsService,
              translateService: TranslateService,
              globalparameterService: GlobalparameterService) {
    super('fromDate', 'SECURITY_PERIODS_FROM_MAX', HistoryquotePeriod, messageToastService, historyquotePeriodService,
      changeDetectionStrategy, filterService, usersettingsService, translateService, globalparameterService);
    this.addColumn(DataType.DateString, this.dataSortKey, 'DATE_FROM', true, false);
    this.addColumn(DataType.Numeric, 'price', 'CLOSE', true, false);
    this.prepareTableAndTranslate();
  }
}
