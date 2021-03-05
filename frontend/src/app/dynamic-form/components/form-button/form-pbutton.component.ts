import {Component} from '@angular/core';
import {FormGroup} from '@angular/forms';

import {FieldFormFormGroupConfig} from '../../models/field.form.form.group.config';
import {FieldConfig} from '../../models/field.config';
import {FormConfig} from '../../models/form.config';

/**
 * Output of a single button, It is assumed that the output of the buttons is from left to right.
 */
@Component({
  selector: 'form-pbutton',
  template: `
      <button *ngIf="!config.buttonFN" pButton class="btn ml-1"
              type="submit" [label]="config.labelKey | translate" [disabled]="!group.valid || config.disabled">
        <svg-icon *ngIf="config.icon"
                  [src]="config.icon" [svgStyle]="{ 'width.px':16, 'height.px':16 }"></svg-icon>
      </button>
      <button *ngIf="config.buttonFN && !config.labelKey.endsWith('_')" pButton class="btn ml-1"
              [disabled]="config.disabled"
              type="button" [label]="config.labelKey | translate" (click)="config.buttonFN($event)">
        <svg-icon *ngIf="config.icon"
          [src]="config.icon" [svgStyle]="{ 'width.px':16, 'height.px':16}"></svg-icon>
      </button>
      <button *ngIf="config.buttonFN && config.labelKey.endsWith('_')" pButton
              class="btn ml-1 button-no-label"
              pTooltip="{{config.labelKey + '_TOOLTIP' | translate | filterOut:config.labelKey + '_TOOLTIP'}}"
              [disabled]="config.disabled" type="button" (click)="config.buttonFN($event)">
        <svg-icon *ngIf="config.icon"
                  [src]="config.icon" [svgStyle]="{ 'width.px':13, 'height.px':13}"></svg-icon>
      </button>
  `
})

export class FormPButtonComponent implements FieldFormFormGroupConfig {
  config: FieldConfig;
  formConfig: FormConfig;
  group: FormGroup;
}

