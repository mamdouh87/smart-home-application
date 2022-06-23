import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SubProjectAttrTemplateService } from '../service/sub-project-attr-template.service';
import { ISubProjectAttrTemplate, SubProjectAttrTemplate } from '../sub-project-attr-template.model';
import { ISubProjectTemplate } from 'app/entities/sub-project-template/sub-project-template.model';
import { SubProjectTemplateService } from 'app/entities/sub-project-template/service/sub-project-template.service';

import { SubProjectAttrTemplateUpdateComponent } from './sub-project-attr-template-update.component';

describe('SubProjectAttrTemplate Management Update Component', () => {
  let comp: SubProjectAttrTemplateUpdateComponent;
  let fixture: ComponentFixture<SubProjectAttrTemplateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let subProjectAttrTemplateService: SubProjectAttrTemplateService;
  let subProjectTemplateService: SubProjectTemplateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SubProjectAttrTemplateUpdateComponent],
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
      .overrideTemplate(SubProjectAttrTemplateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SubProjectAttrTemplateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    subProjectAttrTemplateService = TestBed.inject(SubProjectAttrTemplateService);
    subProjectTemplateService = TestBed.inject(SubProjectTemplateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SubProjectTemplate query and add missing value', () => {
      const subProjectAttrTemplate: ISubProjectAttrTemplate = { id: 456 };
      const subProjectTemplate: ISubProjectTemplate = { id: 83367 };
      subProjectAttrTemplate.subProjectTemplate = subProjectTemplate;

      const subProjectTemplateCollection: ISubProjectTemplate[] = [{ id: 6606 }];
      jest.spyOn(subProjectTemplateService, 'query').mockReturnValue(of(new HttpResponse({ body: subProjectTemplateCollection })));
      const additionalSubProjectTemplates = [subProjectTemplate];
      const expectedCollection: ISubProjectTemplate[] = [...additionalSubProjectTemplates, ...subProjectTemplateCollection];
      jest.spyOn(subProjectTemplateService, 'addSubProjectTemplateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ subProjectAttrTemplate });
      comp.ngOnInit();

      expect(subProjectTemplateService.query).toHaveBeenCalled();
      expect(subProjectTemplateService.addSubProjectTemplateToCollectionIfMissing).toHaveBeenCalledWith(
        subProjectTemplateCollection,
        ...additionalSubProjectTemplates
      );
      expect(comp.subProjectTemplatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const subProjectAttrTemplate: ISubProjectAttrTemplate = { id: 456 };
      const subProjectTemplate: ISubProjectTemplate = { id: 6363 };
      subProjectAttrTemplate.subProjectTemplate = subProjectTemplate;

      activatedRoute.data = of({ subProjectAttrTemplate });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(subProjectAttrTemplate));
      expect(comp.subProjectTemplatesSharedCollection).toContain(subProjectTemplate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubProjectAttrTemplate>>();
      const subProjectAttrTemplate = { id: 123 };
      jest.spyOn(subProjectAttrTemplateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subProjectAttrTemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subProjectAttrTemplate }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(subProjectAttrTemplateService.update).toHaveBeenCalledWith(subProjectAttrTemplate);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubProjectAttrTemplate>>();
      const subProjectAttrTemplate = new SubProjectAttrTemplate();
      jest.spyOn(subProjectAttrTemplateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subProjectAttrTemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subProjectAttrTemplate }));
      saveSubject.complete();

      // THEN
      expect(subProjectAttrTemplateService.create).toHaveBeenCalledWith(subProjectAttrTemplate);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubProjectAttrTemplate>>();
      const subProjectAttrTemplate = { id: 123 };
      jest.spyOn(subProjectAttrTemplateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subProjectAttrTemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(subProjectAttrTemplateService.update).toHaveBeenCalledWith(subProjectAttrTemplate);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSubProjectTemplateById', () => {
      it('Should return tracked SubProjectTemplate primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSubProjectTemplateById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
