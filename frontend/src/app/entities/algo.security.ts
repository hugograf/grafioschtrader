import {Security} from './security';
import {AlgoAssetclassSecurity} from './algo.assetclass.security';
import {AlgoTopAssetSecurity} from './algo.top.asset.security';
import {Exclude} from 'class-transformer';
import {AlgoTreeName} from './view/algo.tree.name';


export class AlgoSecurity extends AlgoAssetclassSecurity implements AlgoTreeName {
  idAlgoSecurityParent: number;
  security: Security = null;

  @Exclude()
  getNameByLanguage(language: String): string {
    return this.security.name + ', ' + this.security.currency;
  }

  @Exclude()
  getChildList(): AlgoTopAssetSecurity[] {
    return null;
  }
}
