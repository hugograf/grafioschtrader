import {Component, Input, OnInit} from '@angular/core';
import {GlobalparameterService} from '../../shared/service/globalparameter.service';
import {TranslateService} from '@ngx-translate/core';
import {AppHelper} from '../../shared/helper/app.helper';
import {CallParam} from '../../shared/maintree/types/dialog.visible';
import {MessageToastService} from '../../shared/message/message.toast.service';
import {WatchlistService} from '../service/watchlist.service';
import {Watchlist} from '../../entities/watchlist';
import {HelpIds} from '../../shared/help/help.ids';
import {SimpleEntityEditBase} from '../../shared/edit/simple.entity.edit.base';
import {DynamicFieldHelper} from '../../shared/helper/dynamic.field.helper';
import {TranslateHelper} from '../../shared/helper/translate.helper';


@Component({
  selector: 'watchlist-edit',
  template: `
    <p-dialog header="{{'WATCHLIST' | translate}}" [(visible)]="visibleDialog"
              [responsive]="true"  [style]="{width: '350px'}"
              (onShow)="onShow($event)" (onHide)="onHide($event)" [modal]="true">

      <dynamic-form [config]="config" [formConfig]="formConfig" [translateService]="translateService" #form="dynamicForm"
                    (submit)="submit($event)">
      </dynamic-form>
    </p-dialog>`
})
export class WatchlistEditComponent extends SimpleEntityEditBase<Watchlist> implements OnInit {

  @Input() callParam: CallParam;

  constructor(translateService: TranslateService,
              globalparameterService: GlobalparameterService,
              messageToastService: MessageToastService,
              watchlistService: WatchlistService) {
    super(HelpIds.HELP_WATCHLIST, 'WATCHLIST', translateService, globalparameterService, messageToastService, watchlistService);
  }

  ngOnInit(): void {
    this.formConfig = AppHelper.getDefaultFormConfig(this.globalparameterService,
      4, this.helpLink.bind(this));

    this.config = [
      DynamicFieldHelper.createFieldInputString('name', 'WATCHLIST_NAME', 25, true),
      DynamicFieldHelper.createSubmitButton()
    ];
    this.configObject = TranslateHelper.prepareFieldsAndErrors(this.translateService, this.config);
  }

  protected initialize(): void {
    this.form.setDefaultValuesAndEnableSubmit();
    if (this.callParam.thisObject != null) {
      this.form.transferBusinessObjectToForm(this.callParam.thisObject);
    }
    setTimeout(() => this.configObject.name.elementRef.nativeElement.focus());
  }


  protected getNewOrExistingInstanceBeforeSave(value: { [name: string]: any }): Watchlist {
    const watchlist: Watchlist = new Watchlist();
    if (this.callParam.thisObject) {
      Object.assign(watchlist, this.callParam.thisObject);
    }
    watchlist.idTenant = this.globalparameterService.getIdTenant();
    this.form.cleanMaskAndTransferValuesToBusinessObject(watchlist);
    return watchlist;
  }


}

