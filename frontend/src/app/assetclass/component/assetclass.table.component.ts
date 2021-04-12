import {ChangeDetectorRef, Component, OnDestroy} from '@angular/core';
import {ActivePanelService} from '../../shared/mainmenubar/service/active.panel.service';
import {TranslateService} from '@ngx-translate/core';
import {UserSettingsService} from '../../shared/service/user.settings.service';
import {DataType} from '../../dynamic-form/models/data.type';
import {GlobalparameterService} from '../../shared/service/globalparameter.service';
import {combineLatest} from 'rxjs';
import {AssetclassService} from '../service/assetclass.service';
import {Assetclass} from '../../entities/assetclass';
import {TableCrudSupportMenu} from '../../shared/datashowbase/table.crud.support.menu';
import {MessageToastService} from '../../shared/message/message.toast.service';
import {HelpIds} from '../../shared/help/help.ids';
import {AssetclassCallParam} from './assetclass.call.param';
import {plainToClass} from 'class-transformer';
import {ConfirmationService, FilterService} from 'primeng/api';
import {DialogService} from 'primeng/dynamicdialog';
import {TranslateValue} from '../../shared/datashowbase/column.config';

/**
 * Shows the asset class as a table.
 */
@Component({
  template: `
    <div class="data-container" (click)="onComponentClick($event)" #cmDiv
         [ngClass]="{'active-border': isActivated(), 'passiv-border': !isActivated()}">

      <p-table [columns]="fields" [value]="entityList" selectionMode="single" [(selection)]="selectedEntity"
               styleClass="sticky-table p-datatable-striped p-datatable-gridlines"
               [dataKey]="entityKeyName" [responsive]="true" sortMode="multiple" [multiSortMeta]="multiSortMeta"
               (sortFunction)="customSort($event)" [customSort]="true">
        <ng-template pTemplate="caption">
          <h4>{{entityNameUpper | translate}}</h4>
        </ng-template>
        <ng-template pTemplate="header" let-fields>
          <tr>
            <th *ngFor="let field of fields" [pSortableColumn]="field.field">
              {{field.headerTranslated}}
              <p-sortIcon [field]="field.field"></p-sortIcon>
            </th>
          </tr>
        </ng-template>
        <ng-template pTemplate="body" let-el let-columns="fields">
          <tr [pSelectableRow]="el">
            <td *ngFor="let field of fields">
              {{getValueByPath(el, field)}}
            </td>
          </tr>
        </ng-template>
      </p-table>
      <p-contextMenu *ngIf="contextMenuItems" [target]="cmDiv" [model]="contextMenuItems"
                     appendTo="body"></p-contextMenu>
    </div>

    <assetclass-edit *ngIf="visibleDialog"
                     [visibleDialog]="visibleDialog"
                     [callParam]="callParam"
                     [proposeChangeEntityWithEntity]=""
                     (closeDialog)="handleCloseDialog($event)">
    </assetclass-edit>
  `,
  providers: [DialogService]
})
export class AssetclassTableComponent extends TableCrudSupportMenu<Assetclass> implements OnDestroy {

  callParam: AssetclassCallParam = new AssetclassCallParam();

  readonly CATEGORY_TYPE = 'categoryType';
  readonly CATEGORY_TYPE_TRANS = this.CATEGORY_TYPE + '$';

  constructor(private assetclassService: AssetclassService,
              confirmationService: ConfirmationService,
              messageToastService: MessageToastService,
              activePanelService: ActivePanelService,
              dialogService: DialogService,
              changeDetectionStrategy: ChangeDetectorRef,
              filterService: FilterService,
              translateService: TranslateService,
              globalparameterService: GlobalparameterService,
              usersettingsService: UserSettingsService) {
    super('Assetclass', assetclassService, confirmationService, messageToastService, activePanelService, dialogService,
      changeDetectionStrategy, filterService, translateService, globalparameterService, usersettingsService);

    this.addColumn(DataType.String, this.CATEGORY_TYPE, 'ASSETCLASS', true, false,
      {translateValues: TranslateValue.NORMAL});
    this.addColumn(DataType.String, 'subCategoryNLS.map.en', 'SUB_ASSETCLASS', true, false, {headerSuffix: 'EN'});
    this.addColumn(DataType.String, 'subCategoryNLS.map.de', 'SUB_ASSETCLASS', true, false, {headerSuffix: 'DE'});
    this.addColumn(DataType.String, 'specialInvestmentInstrument', 'FINANCIAL_INSTRUMENT', true, false,
      {translateValues: TranslateValue.NORMAL});

    this.multiSortMeta.push({field: this.CATEGORY_TYPE, order: 1});
    this.prepareTableAndTranslate();
  }

  prepareCallParm(entity: Assetclass): void {
    this.callParam.hasSecurity = entity && this.hasSecurityObject[this.getId(entity)] !== 0;
    this.callParam.assetclass = entity;
  }

  readData(): void {
    combineLatest([this.assetclassService.getAllAssetclass(),
      this.assetclassService.assetclassesHasSecurity()]).subscribe(data => {
      const assetclassList = plainToClass(Assetclass, data[0]);

      this.callParam.setSuggestionsArrayOfAssetclassList(assetclassList);
      this.createTranslatedValueStoreAndFilterField(assetclassList);
      this.entityList = assetclassList;

      data[1].forEach(keyvalue => this.hasSecurityObject[keyvalue[0]] = keyvalue[1]);
      this.refreshSelectedEntity();
    });
  }

  public getHelpContextId(): HelpIds {
    return HelpIds.HELP_BASEDATA_ASSETCLASS;
  }

  ngOnDestroy(): void {
    this.activePanelService.destroyPanel(this);
  }

}

