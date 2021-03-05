import {Component, Input, OnInit} from '@angular/core';
import {SimpleEntityEditBase} from '../../shared/edit/simple.entity.edit.base';
import {ImportTransactionPlatform} from '../../entities/import.transaction.platform';
import {DataType} from '../../dynamic-form/models/data.type';
import {InputType} from '../../dynamic-form/models/input.type';
import {AppHelper} from '../../shared/helper/app.helper';
import {MessageToastService} from '../../shared/message/message.toast.service';
import {TranslateService} from '@ngx-translate/core';
import {GlobalparameterService} from '../../shared/service/globalparameter.service';
import {HelpIds} from '../../shared/help/help.ids';
import {ImportTransactionPlatformService} from '../service/import.transaction.platform.service';
import {CallParam} from '../../shared/maintree/types/dialog.visible';
import {ValueKeyHtmlSelectOptions} from '../../dynamic-form/models/value.key.html.select.options';
import {Validators} from '@angular/forms';
import {AuditHelper} from '../../shared/helper/audit.helper';
import {Auditable} from '../../entities/auditable';
import {ProposeChangeEntityWithEntity} from '../../entities/proposechange/propose.change.entity.whit.entity';
import {AppSettings} from '../../shared/app.settings';
import {DynamicFieldHelper} from '../../shared/helper/dynamic.field.helper';
import {TranslateHelper} from '../../shared/helper/translate.helper';


@Component({
  selector: 'import-transaction-edit-platform',
  template: `
    <p-dialog header="{{'IMPORTTRANSACTIONPLATFORM' | translate}}" [(visible)]="visibleDialog"
              [responsive]="true" [style]="{width: '400px'}"
              (onShow)="onShow($event)" (onHide)="onHide($event)" [modal]="true">

      <dynamic-form [config]="config" [formConfig]="formConfig" [translateService]="translateService" #form="dynamicForm"
                    (submit)="submit($event)">
      </dynamic-form>
    </p-dialog>`
})
export class ImportTransactionEditPlatformComponent extends SimpleEntityEditBase<ImportTransactionPlatform> implements OnInit {

  @Input() callParam: CallParam;
  @Input() platformTransactionImportHtmlOptions: ValueKeyHtmlSelectOptions[];
  @Input() proposeChangeEntityWithEntity: ProposeChangeEntityWithEntity;

  constructor(translateService: TranslateService,
              globalparameterService: GlobalparameterService,
              messageToastService: MessageToastService,
              importTransactionPlatformService: ImportTransactionPlatformService) {
    super(HelpIds.HELP_BASEDATA_IMPORT_TRANSACTION_TEMPLATE, 'IMPORTTRANSACTIONPLATFORM', translateService, globalparameterService,
      messageToastService, importTransactionPlatformService);
  }


  ngOnInit(): void {
    this.formConfig = AppHelper.getDefaultFormConfig(this.globalparameterService,
      6, this.helpLink.bind(this));

    this.config = [
      DynamicFieldHelper.createFieldInputString('name', 'IMPORT_SET_NAME', 32, true),
      DynamicFieldHelper.createFieldSelectString('idCsvImportImplementation', 'PLATFORM_TRANSACTION_IMPORT', false,
        {valueKeyHtmlOptions: this.platformTransactionImportHtmlOptions}),
      ...AuditHelper.getFullNoteRequestInputDefinition(this.closeDialog, this)
    ];
    this.configObject = TranslateHelper.prepareFieldsAndErrors(this.translateService, this.config);
  }

  protected initialize(): void {
    this.form.setDefaultValuesAndEnableSubmit();
    AuditHelper.transferToFormAndChangeButtonForProposaleEdit(this.translateService, this.globalparameterService,
      <Auditable> this.callParam.thisObject, this.form, this.configObject, this.proposeChangeEntityWithEntity);
    setTimeout(() => this.configObject.name.elementRef.nativeElement.focus());
  }

  protected getNewOrExistingInstanceBeforeSave(value: { [name: string]: any }): ImportTransactionPlatform {
    const importTransactionPlatform = new ImportTransactionPlatform();
    if (this.callParam.thisObject) {
      Object.assign(importTransactionPlatform, this.callParam.thisObject);
    }
    this.form.cleanMaskAndTransferValuesToBusinessObject(importTransactionPlatform);
    return importTransactionPlatform;
  }
}
