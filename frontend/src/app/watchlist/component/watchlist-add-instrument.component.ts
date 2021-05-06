import {Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

import {ProcessedActionData} from '../../shared/types/processed.action.data';
import {ProcessedAction} from '../../shared/types/processed.action';
import {GlobalparameterService} from '../../shared/service/globalparameter.service';
import {SecuritycurrencySearch} from '../../entities/search/securitycurrency.search';
import {AssetclassService} from '../../assetclass/service/assetclass.service';
import {WatchlistAddInstrumentTableComponent} from './watchlist-add-instrument-table.component';
import {SecuritycurrencySearchBase} from '../../securitycurrency/component/securitycurrency.search.base';
import {TenantLimit} from '../../entities/backend/tenant.limit';
import {StockexchangeService} from '../../stockexchange/service/stockexchange.service';
import {BusinessHelper} from '../../shared/helper/business.helper';
import {HelpIds} from '../../shared/help/help.ids';

/**
 * Dialog for adding security or currency pair to a certain watchlist.
 *
 */
@Component({
  selector: 'watchlist-add-instrument',
  template: `
    <p-dialog header="{{'ADD_EXISTING_SECURITY' | translate}}" [(visible)]="visibleAddInstrumentDialog"
              [responsive]="true"[style]="{width: '720px'}"
              (onShow)="onShow($event)" (onHide)="onHide($event)" [modal]="true">


      <dynamic-form [config]="config" [formConfig]="formConfig" [translateService]="translateService"
                    #dynamicFormComponent="dynamicForm" (submit)="submit($event)">
      </dynamic-form>
      <br/>
      <watchlist-add-instrument-table [tenantLimits]="tenantLimits">
      </watchlist-add-instrument-table>
    </p-dialog>`

})
export class WatchlistAddInstrumentComponent extends SecuritycurrencySearchBase {
  // From parent view
  @Input() visibleAddInstrumentDialog: boolean;
  @Input() idWatchlist: number;
  @Input() tenantLimits: TenantLimit[];

  // Access child components
  @ViewChild(WatchlistAddInstrumentTableComponent) waidc: WatchlistAddInstrumentTableComponent;

  // Output for parent view
  @Output() closeDialog = new EventEmitter<ProcessedActionData>();


  constructor(gps: GlobalparameterService,
              assetclassService: AssetclassService,
              stockexchangeService: StockexchangeService,
              translateService: TranslateService) {
    super(true, gps, assetclassService, stockexchangeService, translateService);
  }


  onHide(event) {
    this.closeDialog.emit(new ProcessedActionData(ProcessedAction.NO_CHANGE));
  }


  closeSearchDialog(event): void {
    this.closeDialog.emit(new ProcessedActionData(ProcessedAction.NO_CHANGE));
  }


  childClearList(): void {
    this.waidc.clearList();
  }

  helpLink() {
    BusinessHelper.toExternalHelpWebpage(this.gps.getUserLang(), HelpIds.HELP_WATCHLIST_WATCHLIST);
  }

  childLoadData(securitycurrencySearch: SecuritycurrencySearch): void {
    this.waidc.loadData(this.idWatchlist, securitycurrencySearch);
  }

}

