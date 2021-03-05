import {ChangeDetectorRef, Component} from '@angular/core';
import {CrudMenuOptions, TableCrudSupportMenu} from '../../shared/datashowbase/table.crud.support.menu';
import {ProposeChangeEntity} from '../../entities/propose.change.entity';
import {ActivePanelService} from '../../shared/mainmenubar/service/active.panel.service';
import {GlobalparameterService} from '../../shared/service/globalparameter.service';
import {DataType} from '../../dynamic-form/models/data.type';
import {AssetclassService} from '../../assetclass/service/assetclass.service';
import {MessageToastService} from '../../shared/message/message.toast.service';
import {UserSettingsService} from '../../shared/service/user.settings.service';
import {TranslateService} from '@ngx-translate/core';
import {ProposeChangeEntityService} from '../service/propose.change.entity.service';
import {plainToClass} from 'class-transformer';
import {DialogService} from 'primeng/dynamicdialog';
import {ConfirmationService} from 'primeng/api';


/**
 * Only delete is possible.
 */
@Component({
  template: `
    <div class="data-container" (click)="onComponentClick($event)" #cmDiv
         [ngClass]="{'active-border': isActivated(), 'passiv-border': !isActivated()}">

      <p-table [columns]="fields" [value]="entityList" selectionMode="single" [(selection)]="selectedEntity" [dataKey]="entityKeyName"
               styleClass="sticky-table p-datatable-striped p-datatable-gridlines">
        <ng-template pTemplate="caption">
          <h4>{{'YOUR_CHANGE_REQUESTS' | translate}}</h4>
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
      <p-contextMenu *ngIf="contextMenuItems" [target]="cmDiv" [model]="contextMenuItems" appendTo="body"></p-contextMenu>
    </div>
  `,
  providers: [DialogService]
})
export class YourProposalTableComponent extends TableCrudSupportMenu<ProposeChangeEntity> {

  constructor(private assetclassService: AssetclassService,
              private proposeChangeEntityService: ProposeChangeEntityService,
              confirmationService: ConfirmationService,
              messageToastService: MessageToastService,
              activePanelService: ActivePanelService,
              dialogService: DialogService,
              changeDetectionStrategy: ChangeDetectorRef,
              translateService: TranslateService,
              globalparameterService: GlobalparameterService,
              usersettingsService: UserSettingsService) {
    super('ProposeChangeEntity', proposeChangeEntityService, confirmationService, messageToastService, activePanelService,
      dialogService, changeDetectionStrategy, translateService, globalparameterService, usersettingsService,
      [CrudMenuOptions.Allow_Delete]);

    this.addColumn(DataType.String, 'entity', 'ENTITY_NAME', true, false,
      {translateValues: true});
    this.addColumn(DataType.String, 'noteRequest', 'PROPOSECHANGENOTE', true, false);
    this.addColumn(DataType.String, 'dataChangeState', 'PROPOSE_STATE', true, false,
      {translateValues: true});
    this.addColumn(DataType.String, 'noteAcceptReject', 'PROPOSEACCEPTREJECT', true, false);

    this.prepareTableAndTranslate();
  }

  readData(): void {
    this.proposeChangeEntityService.getProposeChangeEntityListByCreatedBy().subscribe(proposeChangeEntityList => {
      this.createTranslatedValueStoreAndFilterField(proposeChangeEntityList);
      this.entityList = plainToClass(ProposeChangeEntity, proposeChangeEntityList);
    });
  }


  prepareCallParm(entity: ProposeChangeEntity): void {

  }
}
