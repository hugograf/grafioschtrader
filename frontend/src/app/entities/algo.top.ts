import {AlgoTopAssetSecurity} from './algo.top.asset.security';
import {AlgoAssetclass} from './algo.assetclass';
import {Exclude, Type} from 'class-transformer';
import {AlgoTreeName} from './view/algo.tree.name';
import {RuleStrategy} from '../shared/types/rule.strategy';


export class AlgoTop extends AlgoTopAssetSecurity implements AlgoTreeName {
  name: string = null;
  @Type(() => AlgoAssetclass)
  algoAssetclassList: AlgoAssetclass[];
  ruleStrategy: RuleStrategy | string;
  idWatchlist: number = null;
  activatable: boolean;
  addedPercentage: number;

  @Exclude()
  getNameByLanguage(language: String): string {
    return this.name;
  }

  @Exclude()
  getChildList(): AlgoTopAssetSecurity[] {
    return this.algoAssetclassList;
  }
}
