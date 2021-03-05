import {TableConfigBase} from '../../shared/datashowbase/table.config.base';
import {SvgIconRegistryService} from 'angular-svg-icon';
import {ChangeDetectorRef} from '@angular/core';
import {UserSettingsService} from '../../shared/service/user.settings.service';
import {TranslateService} from '@ngx-translate/core';
import {GlobalparameterService} from '../../shared/service/globalparameter.service';
import {ColumnConfig} from '../../shared/datashowbase/column.config';
import {DividendSplit} from '../../entities/dividend.split';
import {DividendSplitSvgCreator} from '../../shared/dividendsplit/dividend.split.svg.creator';


export abstract class DividendSplitTableBase<S extends DividendSplit> extends TableConfigBase {

  data: S[];

  constructor(changeDetectionStrategy: ChangeDetectorRef,
              usersettingsService: UserSettingsService,
              translateService: TranslateService,
              globalparameterService: GlobalparameterService,
              private iconReg: SvgIconRegistryService,
              public keyfield: string, sortField: string,
              public groupTitle: string) {
    super(changeDetectionStrategy, usersettingsService, translateService, globalparameterService);
    this.multiSortMeta.push({field: sortField, order: -1});
    DividendSplitSvgCreator.registerIcons(this.iconReg);
  }

  getCreateTypeIcon(entity: S, field: ColumnConfig): string {
    return DividendSplitSvgCreator.createTypeIconMap[entity.createType];
  }
}
