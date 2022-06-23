import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SubProjectTemplateService } from '../service/sub-project-template.service';
import { ISubProjectTemplate, SubProjectTemplate } from '../sub-project-template.model';
import { IProjectTemplate } from 'app/entities/project-template/project-template.model';
import { ProjectTemplateService } from 'app/entities/project-template/service/project-template.service';

import { SubProjectTemplateUpdateComponent } from './sub-project-template-update.component';

describe('SubProjectTemplate Management Update Component', () => {
  let comp: SubProjectTemplateUpdateComponent;
  let fixture: ComponentFixture<SubProjectTemplateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let subProjectTemplateService: SubProjectTemplateService;
  let projectTemplateService: ProjectTemplateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SubProjectTemplateUpdateComponent],
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
      .overrideTemplate(SubProjectTemplateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SubProjectTemplateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    subProjectTemplateService = TestBed.inject(SubProjectTemplateService);
    projectTemplateService = TestBed.inject(ProjectTemplateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ProjectTemplate query and add missing value', () => {
      const subProjectTemplate: ISubProjectTemplate = { id: 456 };
      const projectTemplate: IProjectTemplate = { id: 71907 };
      subProjectTemplate.projectTemplate = projectTemplate;

      const projectTemplateCollection: IProjectTemplate[] = [{ id: 74295 }];
      jest.spyOn(projectTemplateService, 'query').mockReturnValue(of(new HttpResponse({ body: projectTemplateCollection })));
      const additionalProjectTemplates = [projectTemplate];
      const expectedCollection: IProjectTemplate[] = [...additionalProjectTemplates, ...projectTemplateCollection];
      jest.spyOn(projectTemplateService, 'addProjectTemplateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ subProjectTemplate });
      comp.ngOnInit();

      expect(projectTemplateService.query).toHaveBeenCalled();
      expect(projectTemplateService.addProjectTemplateToCollectionIfMissing).toHaveBeenCalledWith(
        projectTemplateCollection,
        ...additionalProjectTemplates
      );
      expect(comp.projectTemplatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const subProjectTemplate: ISubProjectTemplate = { id: 456 };
      const projectTemplate: IProjectTemplate = { id: 63115 };
      subProjectTemplate.projectTemplate = projectTemplate;

      activatedRoute.data = of({ subProjectTemplate });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(subProjectTemplate));
      expect(comp.projectTemplatesSharedCollection).toContain(projectTemplate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubProjectTemplate>>();
      const subProjectTemplate = { id: 123 };
      jest.spyOn(subProjectTemplateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subProjectTemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subProjectTemplate }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(subProjectTemplateService.update).toHaveBeenCalledWith(subProjectTemplate);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubProjectTemplate>>();
      const subProjectTemplate = new SubProjectTemplate();
      jest.spyOn(subProjectTemplateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subProjectTemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subProjectTemplate }));
      saveSubject.complete();

      // THEN
      expect(subProjectTemplateService.create).toHaveBeenCalledWith(subProjectTemplate);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubProjectTemplate>>();
      const subProjectTemplate = { id: 123 };
      jest.spyOn(subProjectTemplateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subProjectTemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(subProjectTemplateService.update).toHaveBeenCalledWith(subProjectTemplate);
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
  });
});
