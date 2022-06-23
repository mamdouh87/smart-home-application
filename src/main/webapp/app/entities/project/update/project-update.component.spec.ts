import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProjectService } from '../service/project.service';
import { IProject, Project } from '../project.model';
import { IProjectTemplate } from 'app/entities/project-template/project-template.model';
import { ProjectTemplateService } from 'app/entities/project-template/service/project-template.service';
import { IBuildingType } from 'app/entities/building-type/building-type.model';
import { BuildingTypeService } from 'app/entities/building-type/service/building-type.service';

import { ProjectUpdateComponent } from './project-update.component';

describe('Project Management Update Component', () => {
  let comp: ProjectUpdateComponent;
  let fixture: ComponentFixture<ProjectUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let projectService: ProjectService;
  let projectTemplateService: ProjectTemplateService;
  let buildingTypeService: BuildingTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProjectUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProjectUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProjectUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    projectService = TestBed.inject(ProjectService);
    projectTemplateService = TestBed.inject(ProjectTemplateService);
    buildingTypeService = TestBed.inject(BuildingTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call projectTemplate query and add missing value', () => {
      const project: IProject = { id: 456 };
      const projectTemplate: IProjectTemplate = { id: 81958 };
      project.projectTemplate = projectTemplate;

      const projectTemplateCollection: IProjectTemplate[] = [{ id: 80710 }];
      jest.spyOn(projectTemplateService, 'query').mockReturnValue(of(new HttpResponse({ body: projectTemplateCollection })));
      const expectedCollection: IProjectTemplate[] = [projectTemplate, ...projectTemplateCollection];
      jest.spyOn(projectTemplateService, 'addProjectTemplateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ project });
      comp.ngOnInit();

      expect(projectTemplateService.query).toHaveBeenCalled();
      expect(projectTemplateService.addProjectTemplateToCollectionIfMissing).toHaveBeenCalledWith(
        projectTemplateCollection,
        projectTemplate
      );
      expect(comp.projectTemplatesCollection).toEqual(expectedCollection);
    });

    it('Should call buildingType query and add missing value', () => {
      const project: IProject = { id: 456 };
      const buildingType: IBuildingType = { id: 79589 };
      project.buildingType = buildingType;

      const buildingTypeCollection: IBuildingType[] = [{ id: 60105 }];
      jest.spyOn(buildingTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: buildingTypeCollection })));
      const expectedCollection: IBuildingType[] = [buildingType, ...buildingTypeCollection];
      jest.spyOn(buildingTypeService, 'addBuildingTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ project });
      comp.ngOnInit();

      expect(buildingTypeService.query).toHaveBeenCalled();
      expect(buildingTypeService.addBuildingTypeToCollectionIfMissing).toHaveBeenCalledWith(buildingTypeCollection, buildingType);
      expect(comp.buildingTypesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const project: IProject = { id: 456 };
      const projectTemplate: IProjectTemplate = { id: 26753 };
      project.projectTemplate = projectTemplate;
      const buildingType: IBuildingType = { id: 78297 };
      project.buildingType = buildingType;

      activatedRoute.data = of({ project });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(project));
      expect(comp.projectTemplatesCollection).toContain(projectTemplate);
      expect(comp.buildingTypesCollection).toContain(buildingType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Project>>();
      const project = { id: 123 };
      jest.spyOn(projectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ project });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: project }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(projectService.update).toHaveBeenCalledWith(project);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Project>>();
      const project = new Project();
      jest.spyOn(projectService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ project });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: project }));
      saveSubject.complete();

      // THEN
      expect(projectService.create).toHaveBeenCalledWith(project);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Project>>();
      const project = { id: 123 };
      jest.spyOn(projectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ project });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(projectService.update).toHaveBeenCalledWith(project);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackProjectTemplateById', () => {
      it('Should return tracked ProjectTemplate primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProjectTemplateById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackBuildingTypeById', () => {
      it('Should return tracked BuildingType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackBuildingTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
