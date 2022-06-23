import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'project-template',
        data: { pageTitle: 'smartHomeApplicationApp.projectTemplate.home.title' },
        loadChildren: () => import('./project-template/project-template.module').then(m => m.ProjectTemplateModule),
      },
      {
        path: 'sub-project-template',
        data: { pageTitle: 'smartHomeApplicationApp.subProjectTemplate.home.title' },
        loadChildren: () => import('./sub-project-template/sub-project-template.module').then(m => m.SubProjectTemplateModule),
      },
      {
        path: 'sub-project-attr-template',
        data: { pageTitle: 'smartHomeApplicationApp.subProjectAttrTemplate.home.title' },
        loadChildren: () =>
          import('./sub-project-attr-template/sub-project-attr-template.module').then(m => m.SubProjectAttrTemplateModule),
      },
      {
        path: 'project',
        data: { pageTitle: 'smartHomeApplicationApp.project.home.title' },
        loadChildren: () => import('./project/project.module').then(m => m.ProjectModule),
      },
      {
        path: 'building-type',
        data: { pageTitle: 'smartHomeApplicationApp.buildingType.home.title' },
        loadChildren: () => import('./building-type/building-type.module').then(m => m.BuildingTypeModule),
      },
      {
        path: 'sub-project',
        data: { pageTitle: 'smartHomeApplicationApp.subProject.home.title' },
        loadChildren: () => import('./sub-project/sub-project.module').then(m => m.SubProjectModule),
      },
      {
        path: 'sub-project-attr',
        data: { pageTitle: 'smartHomeApplicationApp.subProjectAttr.home.title' },
        loadChildren: () => import('./sub-project-attr/sub-project-attr.module').then(m => m.SubProjectAttrModule),
      },
      {
        path: 'hardware-items',
        data: { pageTitle: 'smartHomeApplicationApp.hardwareItems.home.title' },
        loadChildren: () => import('./hardware-items/hardware-items.module').then(m => m.HardwareItemsModule),
      },
      {
        path: 'project-items-requirement',
        data: { pageTitle: 'smartHomeApplicationApp.projectItemsRequirement.home.title' },
        loadChildren: () =>
          import('./project-items-requirement/project-items-requirement.module').then(m => m.ProjectItemsRequirementModule),
      },
      {
        path: 'requirement-item',
        data: { pageTitle: 'smartHomeApplicationApp.requirementItem.home.title' },
        loadChildren: () => import('./requirement-item/requirement-item.module').then(m => m.RequirementItemModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
