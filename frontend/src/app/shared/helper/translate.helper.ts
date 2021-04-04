import {TranslateService} from '@ngx-translate/core';
import {FieldFormGroup, FormGroupDefinition} from '../../dynamic-form/models/form.group.definition';
import {FieldConfig} from '../../dynamic-form/models/field.config';
import {FormHelper} from '../../dynamic-form/components/FormHelper';
import {MenuItem} from 'primeng/api';
import {ColumnConfig} from '../datashowbase/column.config';
import {Helper} from '../../helper/helper';
import {AppSettings} from '../app.settings';

export class TranslateHelper {

  public static prepareFieldsAndErrors(translateService: TranslateService,
                                       fieldFormGroup: FieldFormGroup[]): { [name: string]: FieldConfig } {
    const fieldConfigs = FormHelper.flattenConfigMap(fieldFormGroup);
    const flattenFieldConfigObject: { [name: string]: FieldConfig } = Object.assign({}, ...fieldConfigs.map(d => ({[d.field]: d})),
      ...FormHelper.getFormGroupDefinition(fieldFormGroup).map(d => ({[d.formGroupName]: d})));

    this.translateMessageErrors(translateService, fieldFormGroup);
    return flattenFieldConfigObject;
  }

  public static translateMessageErrors(translateService: TranslateService, fieldConfigs: FieldFormGroup[]) {
    fieldConfigs.forEach((fieldConfig: FieldFormGroup) => {
      if (!FormHelper.isFieldConfig(fieldConfig)) {
        this.translateMessageErrors(translateService, (<FormGroupDefinition>fieldConfig).fieldConfig);
      } else {
        fieldConfigs.filter(fc => (<FieldConfig>fc).labelHelpText &&
          (/^[A-Z,_]*$/).test((<FieldConfig>fc).labelHelpText)).forEach(fc =>
          this.translateLabelHelpText(translateService, <FieldConfig>fc));

        fieldConfigs.filter(() => (<FieldConfig>fieldConfig).errors).forEach(() =>
          this.translateMessageError(translateService, <FieldConfig>fieldConfig));
      }
    });
  }


  public static translateLabelHelpText(translateService: TranslateService, fieldConfig: FieldConfig) {
    translateService.get(fieldConfig.labelHelpText).subscribe(transText => fieldConfig.labelHelpText
      = transText);
  }

  public static translateMessageError(translateService: TranslateService, fieldConfig: FieldConfig) {
    fieldConfig.errors.forEach(error => translateService.get(error.keyi18n, {
      param1: error.param1,
      param2: error.param2
    })
      .subscribe(text => error.text = text));
  }


  public static translateMenuItems(menuItems: MenuItem[], translateService: TranslateService, translateParam = true) {
    menuItems.forEach(menuItem => {
      if (menuItem.label) {
        if (menuItem.label.startsWith('_')) {
          menuItem.label = menuItem.label.slice(1);
          menuItem.title =  TranslateHelper.cutOffDialogDots(menuItem.label) + '_TITLE';
        }
        TranslateHelper.translateMenuItem(menuItem, 'label', translateService, translateParam);
        TranslateHelper.translateMenuItem(menuItem, 'title', translateService, translateParam);
        if (menuItem.items) {
          // For child menu
          this.translateMenuItems(<MenuItem[]>menuItem.items, translateService, translateParam);
        }
      }
    });
  }

  private static translateMenuItem(menuItem: MenuItem, targetProperty: string, translateService: TranslateService, translateParam): void {
    if (menuItem[targetProperty] && menuItem[targetProperty].toUpperCase() === menuItem[targetProperty]) {
      // Translate only once
      const dialogMenuItem = menuItem[targetProperty].endsWith(AppSettings.DIALOG_MENU_SUFFIX);
      if (dialogMenuItem) {
        menuItem[targetProperty] = TranslateHelper.cutOffDialogDots(menuItem[targetProperty]);
      }
      if (menuItem[targetProperty].indexOf('|') >= 0) {
        const labelWord: string[] = menuItem[targetProperty].split('|');

        if (translateParam) {
          translateService.get(labelWord[1]).subscribe(param =>
            translateService.get(labelWord[0], {i18nRecord: param}).subscribe(message =>
              menuItem[targetProperty] = message + (dialogMenuItem ? AppSettings.DIALOG_MENU_SUFFIX : ''))
          );
        } else {
          translateService.get(labelWord[0], {i18nRecord: labelWord[1]}).subscribe(
            message => menuItem[targetProperty] = message);
        }
      } else {
        translateService.get(menuItem[targetProperty]).subscribe(translated => menuItem[targetProperty] =
          translated + (dialogMenuItem ? AppSettings.DIALOG_MENU_SUFFIX : ''));
      }
    }
  }

  private static cutOffDialogDots(label: string): string {
    return label.endsWith(AppSettings.DIALOG_MENU_SUFFIX) ? label.slice(0, -AppSettings.DIALOG_MENU_SUFFIX.length) : label;
  }


  /**
   * Create a column which contains a translated value. A $ is added to the existing field name, which will bo shown.
   *
   * @param translateService Translation service
   * @param fields All fields also fields which does not need a translation.
   * @param data Which will be translated.
   */
  public static createTranslatedValueStore(translateService: TranslateService, fields: ColumnConfig[], data: any[]): void {
    const columnConfigs = fields.filter(columnConfig => !!columnConfig.translateValues);
    if (columnConfigs.length > 0) {

      data.forEach(datavalue => {
        columnConfigs.forEach(columnConfig => {
          const value = Helper.getValueByPath(datavalue, columnConfig.field);
          if (!columnConfig.translatedValueMap) {
            columnConfig.translatedValueMap = {};
          }
          if (!columnConfig.translatedValueMap.hasOwnProperty(value)) {
            if (value) {
              // Add value and translation
              translateService.get(value).subscribe(translated => {
                columnConfig.translatedValueMap[value] = translated;
                // Expand data with a field that contains the value
                Helper.setValueByPath(datavalue, columnConfig.field + '$', translated);
              });
            }
          } else {
            // Expand data with a field and existing translation
            Helper.setValueByPath(datavalue, columnConfig.field + '$', columnConfig.translatedValueMap[value]);
          }
        });
      });
      columnConfigs.forEach(columnConfig => columnConfig.fieldTranslated = columnConfig.field + '$');
    }
  }

}
