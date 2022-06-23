import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProjectItemsRequirementService } from '../service/project-items-requirement.service';
import { IProjectItemsRequirement, ProjectItemsRequirement } from '../project-items-requirement.model';
import { IRequirementItem } from 'app/entities/requirement-item/requirement-item.model';
import { RequirementItemService } from 'app/entities/requirement-item/service/requirement-item.service';
import { ISubProject } from 'app/entities/sub-project/sub-project.model';
import { SubProjectService } from 'app/entities/sub-project/service/sub-project.service';

import { ProjectItemsRequirementUpdateComponent } from './project-items-requirement-update.component';

describe('ProjectItemsRequirement Management Update Component', () => {
  let comp: ProjectItemsRequirementUpdateComponent;
  let fixture: ComponentFixture<ProjectItemsRequirementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let projectItemsRequirementService: ProjectItemsRequirementService;
  let requirementItemService: RequirementItemService;
  let subProjectService: SubProjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProjectItemsRequirementUpdateComponent],
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
      .overrideTemplate(ProjectItemsRequirementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProjectItemsRequirementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    projectItemsRequirementService = TestBed.inject(ProjectItemsRequirementService);
    requirementItemService = TestBed.inject(RequirementItemService);
    subProjectService = TestBed.inject(SubProjectService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call requirementItems query and add missing value', () => {
      const projectItemsRequirement: IProjectItemsRequirement = { id: 456 };
      const requirementItems: IRequirementItem = { id: 55590 };
      projectItemsRequirement.requirementItems = requirementItems;

      const requirementItemsCollection: IRequirementItem[] = [{ id: 65016 }];
      jest.spyOn(requirementItemService, 'query').mockReturnValue(of(new HttpResponse({ body: requirementItemsCollection })));
      const expectedCollection: IRequirementItem[] = [requirementItems, ...requirementItemsCollection];
      jest.spyOn(requirementItemService, 'addRequirementItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ projectItemsRequirement });
      comp.ngOnInit();

      expect(requirementItemService.query).toHaveBeenCalled();
      expect(requirementItemService.addRequirementItemToCollectionIfMissing).toHaveBeenCalledWith(
        requirementItemsCollection,
        requirementItems
      );
      expect(comp.requirementItemsCollection).toEqual(expectedCollection);
    });

    it('Should call SubProject query and add missing value', () => {
      const projectItemsRequirement: IProjectItemsRequirement = { id: 456 };
      const subProject: ISubProject = { id: 59887 };
      projectItemsRequirement.subProject = subProject;

      const subProjectCollection: ISubProject[] = [{ id: 48472 }];
      jest.spyOn(subProjectService, 'query').mockReturnValue(of(new HttpResponse({ body: subProjectCollection })));
      const additionalSubProjects = [subProject];
      const expectedCollection: ISubProject[] = [...additionalSubProjects, ...subProjectCollection];
      jest.spyOn(subProjectService, 'addSubProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ projectItemsRequirement });
      comp.ngOnInit();

      expect(subProjectService.query).toHaveBeenCalled();
      expect(subProjectService.addSubProjectToCollectionIfMissing).toHaveBeenCalledWith(subProjectCollection, ...additionalSubProjects);
      expect(comp.subProjectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const projectItemsRequirement: IProjectItemsRequirement = { id: 456 };
      const requirementItems: IRequirementItem = { id: 26180 };
      projectItemsRequirement.requirementItems = requirementItems;
      const subProject: ISubProject = { id: 28278 };
      projectItemsRequirement.subProject = subProject;

      activatedRoute.data = of({ projectItemsRequirement });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(projectItemsRequirement));
      expect(comp.requirementItemsCollection).toContain(requirementItems);
      expect(comp.subProjectsSharedCollection).toContain(subProject);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProjectItemsRequirement>>();
      const projectItemsRequirement = { id: 123 };
      jest.spyOn(projectItemsRequirementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectItemsRequirement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projectItemsRequirement }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(projectItemsRequirementService.update).toHaveBeenCalledWith(projectItemsRequirement);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProjectItemsRequirement>>();
      const projectItemsRequirement = new ProjectItemsRequirement();
      jest.spyOn(projectItemsRequirementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectItemsRequirement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projectItemsRequirement }));
      saveSubject.complete();

      // THEN
      expect(projectItemsRequirementService.create).toHaveBeenCalledWith(projectItemsRequirement);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProjectItemsRequirement>>();
      const projectItemsRequirement = { id: 123 };
      jest.spyOn(projectItemsRequirementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectItemsRequirement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(projectItemsRequirementService.update).toHaveBeenCalledWith(projectItemsRequirement);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackRequirementItemById', () => {
      it('Should return tracked RequirementItem primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRequirementItemById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSubProjectById', () => {
      it('Should return tracked SubProject primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSubProjectById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
